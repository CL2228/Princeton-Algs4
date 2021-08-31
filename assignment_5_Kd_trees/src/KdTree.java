import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;



public class KdTree {
    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;

    private Node root;
    private int size;


    public KdTree() {       // initiation
        root = null;
        size = 0;
    }


    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle correspoinding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private boolean orientation;    // vertical of horizontal

        public Node(Point2D point2D, RectHV re, boolean ori) {
            this.p = point2D;
            this.rect = re;
            this.orientation = ori;
            this.lb = null;
            this.rt = null;
        }
    }


    public boolean isEmpty() { return root == null; }


    public int size() { return size; }


    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("?");

        if (!contains(p))
            root = insert(root, null, p, VERTICAL, false);
    }
    private Node insert(Node x, Node parent, Point2D needCreatPoint, boolean orien, boolean lessOrBigger) {
        /*
            x: current Node
            parent：  parent Node, used for calculate the rectangle of sons
            needCreatPoint: 2D point coordinates needed to be inserted
            orien: the orientation of [this] Node
            lessOrBigger: this Node is less/bigger than the parent? used for decide the rectangle
                            true = less
         */

        if (x == null) {
            // if this Node is null, then creat a new one, orientation of which is identical to parameter

            RectHV tempRec;
            if (parent == null) {
                // for root Node
                tempRec = new RectHV(0, 0, 1, 1);
            }
            else {
                if (lessOrBigger) {  // left/below
                    if (parent.orientation == HORIZONTAL) {
                        // its father is a horizontal one, then segment the rectangle horizontally
                        tempRec = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.rect.xmax(), parent.p.y());
                    } else {
                        // its father is a vertical one, segment the rectangle vertically
                        tempRec = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.p.x(), parent.rect.ymax());
                    }
                } else {        // right/top
                    if (parent.orientation == HORIZONTAL) {
                        // the same as before, except that we now using top one
                        tempRec = new RectHV(parent.rect.xmin(), parent.p.y(), parent.rect.xmax(), parent.rect.ymax());
                    } else {
                        // using the right fragment
                        tempRec = new RectHV(parent.p.x(), parent.rect.ymin(), parent.rect.xmax(), parent.rect.ymax());
                    }
                }
            }
            this.size++;
            return new Node(needCreatPoint, tempRec, orien);
        }

        // if this Node is not null and it should continue recursively
        if (orien == VERTICAL) {    // this current one is vertical
            if (needCreatPoint.x() < x.p.x()) {
                x.lb = insert(x.lb, x, needCreatPoint, !(x.orientation), true);
            }
            else {
                x.rt = insert(x.rt, x, needCreatPoint, !(x.orientation), false);
            }
        } else {    // this current one is horizontal
            if (needCreatPoint.y() < x.p.y()) {
                x.lb = insert(x.lb, x, needCreatPoint, !(x.orientation), true);
            }
            else {
                x.rt = insert(x.rt, x, needCreatPoint, !(x.orientation), false);
            }
        }

        return x;
    }



    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("?");
        return contains(root, p);
    }
    private boolean contains(Node x, Point2D query) {
        if (x == null) return false;
        if (x.p.equals(query)) return true;

        if (x.orientation == HORIZONTAL) {
            if (query.y() < x.p.y()) return contains(x.lb, query);
            else return contains(x.rt, query);
        } else {
            if (query.x() < x.p.x()) return contains(x.lb, query);
            else return contains(x.rt, query);
        }
    }



    public void draw() {
        draw(root);
    }
    private void draw(Node x) {
        if (x != null) {
            if (x.orientation == VERTICAL) {
                StdDraw.setPenRadius(0.005);
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
                StdDraw.setPenRadius(0.01);
                StdDraw.setPenColor(StdDraw.BLACK);
                x.p.draw();
            } else {
                StdDraw.setPenRadius(0.005);
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
                StdDraw.setPenRadius(0.01);
                StdDraw.setPenColor(StdDraw.BLACK);
                x.p.draw();
            }
            draw(x.lb);
            draw(x.rt);
        }
    }



    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("?");

        if (isEmpty()) return null;
        else {
            Queue<Point2D> queue = new Queue<Point2D>();

            range(queue, root, rect);

            return queue;
        }

    }
    private void range(Queue<Point2D> que, Node x, RectHV bar) {
        if (bar.contains(x.p)) {
            que.enqueue(x.p);
        }
        if (x.lb != null && x.lb.rect.intersects(bar)) range(que, x.lb, bar);
        if (x.rt != null && x.rt.rect.intersects(bar)) range(que, x.rt, bar);
    }



    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("?");

        if (isEmpty()) return null;
        else return nearest(root, p, root.p, p.distanceSquaredTo(root.p));
    }
    // 找最小的逻辑：
    // 1. 走到这个节点时候，比较这个节点的距离，更新最小距离
    // 2. 看当前最小的最小距离是不是小于和两个son的rect的距离，
    //    如果小于其中一个，接下来就直接不找（这其中还要看有没有子节点
    // 3. 如果当前最短的距离都大于两个son的rect距离，那么就先往最最近的那个找，递归完这一测后再返回去找另一侧的
    private Point2D nearest(Node x, Point2D query, Point2D cureentBest, double cureentNearestDis) {
        Point2D best;
        double nearestDis;

//        StdOut.println("\nbefore compare best: " + cureentBest + "   Dsitance: "+ cureentNearestDis);
        double tempDis = x.p.distanceSquaredTo(query);  //   如果这个点的距离拉近了则更新
        if (tempDis < cureentNearestDis) {
            best = x.p;
            nearestDis = x.p.distanceSquaredTo(query);
        } else {            // 否则信息保持不变
            best = cureentBest;
            nearestDis = cureentNearestDis;
        }

//        StdOut.println("after compare best" + best + "   Distance: " + nearestDis);


        if (x.lb != null && nearestDis > pointDisToRec(query, x.lb.rect) && x.rt == null) {
            // 只有lb这一侧是有元素的，切当前的距离大于和lb_rect的距离，则继续往lb找
            Point2D leftBest = nearest(x.lb, query, best, nearestDis);

            if (query.distanceSquaredTo(leftBest) < nearestDis) return leftBest;
            else return best;
        } else if (x.rt != null && nearestDis > pointDisToRec(query, x.rt.rect) && x.lb == null) {
            // 只有rt这一侧有元素，切离rt rect的距离小于当前最大距离，则继续往rt找
            Point2D rightBest = nearest(x.rt, query, best, nearestDis);

            if (query.distanceSquaredTo(rightBest) < nearestDis) return rightBest;
            else return best;


        } else if (x.lb != null && x.rt != null) {
            // 如果两边都不是空的
            double leftDis = pointDisToRec(query, x.lb.rect);
            double rightDis = pointDisToRec(query, x.rt.rect);
//            StdOut.println("left distance : " + leftDis + "  right distance : " + rightDis);
            if (leftDis < rightDis) {
                // 如果左边那块比较近，则先继续往左边找
                Point2D leftBest = nearest(x.lb, query, best, nearestDis);
                if (query.distanceSquaredTo(leftBest) < nearestDis) {
                    // 如果左边的更近, 则更新当前的最近点和最近距离
                    best = leftBest;
                    nearestDis = query.distanceSquaredTo(leftBest);
                }
                if (nearestDis > pointDisToRec(query, x.rt.rect)) {
                    // 如果此时右边那块还有可能有更近的点
                    Point2D rightBest = nearest(x.rt, query, best, nearestDis);
                    if (query.distanceSquaredTo(rightBest) < nearestDis)
                        return rightBest;
                    else return best;
                } else return best;


            } else {
                // 如果右边那块比较近，则先继续往右边找，和上面反过来
                Point2D rightBest = nearest(x.rt, query, best, nearestDis);
//                StdOut.println("right best:" + rightBest + "  right distance" + query.distanceSquaredTo(rightBest));

                if (query.distanceSquaredTo(rightBest) < nearestDis) {

                    // 如果右边的更近，则更新当前最近点和最近距离
                    best = rightBest;
                    nearestDis = query.distanceSquaredTo(rightBest);
                }
                if (nearestDis > pointDisToRec(query, x.lb.rect)) {
                    // 如果左边还可能有更近的点，
                    Point2D leftBest = nearest(x.lb, query, best, nearestDis);
                    if (query.distanceSquaredTo(leftBest) < nearestDis)
                        return leftBest;
                    else return best;
                } else return best;
            }


        } else {
            // 如果两边都是空的，则直接返回当前的最佳point
            return best;
        }

    }
    private double pointDisToRec(Point2D p, RectHV rec) {
        if (rec.contains(p)) return 0;

        if (p.y() <= rec.ymax() && p.y() >= rec.ymin())
            return Math.pow(Math.min(Math.abs(rec.xmax() - p.x()), Math.abs(rec.xmin() - p.x())), 2);
        else if (p.x() <= rec.xmax() && p.x() >= rec.xmin())
            return Math.pow(Math.min(Math.abs(rec.ymax() - p.y()), Math.abs(rec.ymin() - p.y())), 2);

        else if (p.x() < rec.xmin() && p.y() < rec.ymin())
            return Math.pow(rec.ymin() - p.y(), 2) + Math.pow(rec.xmin() - p.x(), 2);
        else if (p.x() < rec.xmin() && p.y() > rec.ymax())
            return Math.pow(rec.ymax() - p.y(), 2) + Math.pow(rec.xmin() - p.x(), 2);
        else if (p.x() > rec.xmax() && p.y() < rec.ymin())
            return Math.pow(rec.ymin() - p.y(), 2) + Math.pow(rec.xmax() - p.x(), 2);
        else return Math.pow(rec.ymax() - p.y(), 2) + Math.pow(rec.xmax() - p.x(), 2);
    }




// correct one is (0.083, 0.051)
// wrong one is (0.226, 0.577)
    public static void main(String[] args) {
        // initialize the two data structures with point from file
        String filename = "input10.txt";
        In in = new In(filename);
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }
        // 0.1 0.6

        Point2D qu = new Point2D(0.1, 0.6);
//        StdOut.println(brute.nearest(qu));
//        StdOut.println(brute.nearest(qu).distanceSquaredTo(qu));
        Point2D best = kdtree.nearest(qu);
        StdOut.println("\nhahah");
        StdOut.println(best);
        StdOut.println(best.distanceSquaredTo(qu));



    }
}
