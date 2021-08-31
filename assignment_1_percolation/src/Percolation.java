import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF id;
    private boolean[] condition;        // record open
    private final int N;
    private int openNum;

    private boolean boundDetection(int i, int j) {
        return !(i <= 0 || i > N || j <= 0 || j > N);
    }
    private int dimentionTransfer(int i, int j) {
        return (i - 1) * N + j;
    }

    // creates n-by-n grid, with all sites initially blocked  TODO:change order
    public Percolation(int n){
        if (n > 0) {
            N = n;
            id = new WeightedQuickUnionUF(n * n +2);        // 加上头和尾
            condition = new boolean[n * n +1];
        } else throw new IllegalArgumentException("?");
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {






        if (boundDetection(row, col)) {
            if (!condition[dimentionTransfer(row, col)]) {
                condition[dimentionTransfer(row, col)] = true;
                openNum += 1;

                if(row==1){
                    id.union(0, dimentionTransfer(row, col));
                    if (N == 1){
                        id.union(N*N+1, dimentionTransfer(row, col));
                    }
                }
                if(row == N){
                                                                                //防止反向穿底连接
                        id.union(N*N+1, dimentionTransfer(row, col));

                }


                if (boundDetection(row - 1, col) && condition[dimentionTransfer(row - 1, col)])
                    id.union(dimentionTransfer(row, col), dimentionTransfer(row - 1, col));
                if (boundDetection(row + 1, col) && condition[dimentionTransfer(row + 1, col)])
                    id.union(dimentionTransfer(row+1, col), dimentionTransfer(row, col));
                if (boundDetection(row, col - 1) && condition[dimentionTransfer(row, col - 1)])
                    id.union((dimentionTransfer(row, col-1)), dimentionTransfer(row, col));
                if (boundDetection(row, col + 1) && condition[dimentionTransfer(row, col + 1)])
                    id.union(dimentionTransfer(row, col+1), dimentionTransfer(row, col));




            }
        }
        else throw new IllegalArgumentException("out of bounds");
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if(boundDetection(row, col)) {
            return condition[dimentionTransfer(row, col)];
        }
        else throw new IllegalArgumentException("out of bounds");

    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (boundDetection(row, col)) {
            if (id.connected(0, dimentionTransfer(row, col))) {
                return true;
            }
            return false;
        }
        else throw new IllegalArgumentException("out ouf bounds");
    }

    // returns the number of open sites
    public int numberOfOpenSites() { return openNum; }

    // does the system percolate?
    public boolean percolates() {
        return (id.connected(0, N * N + 1));
    }

    // test client (optional)
    public static void main(String[] args){

        Percolation a = new Percolation(3);


        a.open(1,3);
        System.out.println(a.isFull(1, 3));

        a.open(2,3);
        System.out.println(a.isFull(2, 3));

        a.open(3,3);
        System.out.println(a.isFull(3, 3));

        a.open(3,1);
        System.out.println(a.isFull(3, 1));

        a.open(3,2);
        System.out.println(a.isFull(3, 1));


        a.open(2,1);
        System.out.println(a.isFull(2, 1));

        a.open(1,1);
        System.out.println(a.isFull(1, 1));

        System.out.println(a.isFull(3, 1));

        /*
        Percolation a = new Percolation(50);
        WeightedQuickUnionUF b = new WeightedQuickUnionUF(50);*/
    }

}
