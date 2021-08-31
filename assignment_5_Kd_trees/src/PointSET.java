import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


public class PointSET {
    private SET<Point2D> bst;


    public PointSET() {
        bst = new SET<Point2D>();
    }

    public boolean isEmpty() { return bst.isEmpty(); }

    public int size() { return bst.size(); }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("?");
        if (!bst.contains(p)) bst.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("?");
        return bst.contains(p);
    }

    public void draw() {
        for (Point2D p : bst) {
            StdDraw.setPenRadius(0.02);
            StdDraw.setPenColor(StdDraw.BLACK);
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("?");

        Queue<Point2D> queue = new Queue<Point2D>();

        for (Point2D p : bst) {
            if (rect.contains(p)) queue.enqueue(p);
        }

        return queue;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("?");

        if (isEmpty()) return null;
        else {
            int count = 0;
            double smallestDIs = 0;
            Point2D nearest = new Point2D(0, 0);

            for (Point2D point : bst) {
                if (count == 0) {
                    nearest = point;
                    smallestDIs = p.distanceSquaredTo(point);
                } else {
                    if (p.distanceSquaredTo(point) < smallestDIs) {
                        smallestDIs = p.distanceSquaredTo(point);
                        nearest = point;
                    }
                }
                count++;
            }
            return nearest;
        }
    }



    public static void main(String[] args) {
        Point2D a = new Point2D(0.3,0.4);
        Point2D b = new Point2D(0.5,0.600);
        Point2D c = new Point2D(0.3, 0.3);

        PointSET pointSET = new PointSET();
        pointSET.insert(a);
        pointSET.insert(b);
        StdOut.println(pointSET.contains(a));
        StdOut.println(pointSET.isEmpty());


    }



}
