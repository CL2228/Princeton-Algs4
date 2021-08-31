import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;


public class Board {
    private final int N;
    private final int[][] data;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        N = tiles.length;
        data = new int[tiles.length][tiles[0].length];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) data[i][j] = tiles[i][j];
        }
    }

    // string representation of this board
    public String toString() {
        String rtn = "";
        rtn += String.valueOf(N);
        for (int i = 0; i < N; i++) {
            rtn += "\n";
            for (int j = 0; j < N; j++) {
                rtn += "\t";
                rtn += String.valueOf(data[i][j]);
            }
        }
        return rtn;
    }

    // board dimension n
    public int dimension() {
        return N;
    }


    // number of tiles out of place
    public int hamming() {
        int distance = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (data[i][j] ==0 ) continue;
                else {
                    if (data[i][j] != i*N + j + 1) distance++;
                }
            }
        }
        return distance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (data[i][j] == 0) continue;
                else distance += calMahanttan(data[i][j], i, j);
            }
        }
        return distance;
    }
    private int calMahanttan(int val, int i, int j) {
        int rtn = 0;
        int desiredi = (val - 1) / N;
        int desiredj = (val - 1) % N;
        if (desiredi - i < 0) rtn += i - desiredi;
        else rtn += desiredi - i;
        if (desiredj - j < 0) rtn += j - desiredj;
        else rtn += desiredj - j;
        return rtn;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == N - 1 && j == N - 1) continue;
                if (data[i][j] != i*N + j + 1) return false;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;

        if (y == null) return false;

        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;

        if (this.data[0].length != that.data[0].length) return false;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.data[i][j] != that.data[i][j]) return false;
            }
        }
        return true;
    }

    private int[][] swap(int r1, int c1, int r2, int c2) {
        int[][] copy = copy();
        int tmp = copy[r1][c1];
        copy[r1][c1] = copy[r2][c2];
        copy[r2][c2] = tmp;
        return copy;
    }

    private int[][] copy() {
        int[][] cpy = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) cpy[i][j] = data[i][j];
        }
        return cpy;
    }


    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<Board>();
        int zeroI = 0, zeroJ = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (data[i][j] == 0) {
                    zeroI = i;
                    zeroJ = j;
                }
            }
        }
        if (zeroI > 0) stack.push(new Board(swap(zeroI, zeroJ, zeroI - 1, zeroJ)));
        if (zeroI < N - 1) stack.push(new Board(swap(zeroI, zeroJ, zeroI + 1, zeroJ)));
        if (zeroJ > 0) stack.push(new Board(swap(zeroI, zeroJ, zeroI, zeroJ - 1)));
        if (zeroJ < N - 1) stack.push(new Board(swap(zeroI, zeroJ, zeroI, zeroJ + 1)));
        return stack;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] temp = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) temp[i][j] = data[i][j];
        }
        int swap1i = 0, swap1j = 0, swap2i = 0, swap2j = 0;
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++){
                if (temp[i][j] !=0) {
                    count++;
                    if (count == 1) {
                        swap1i = i;
                        swap1j = j;
                    }
                    if (count == 2) {
                        swap2i = i;
                        swap2j = j;
                        break;
                    }
                }
            }
        }
        int tmp = temp[swap1i][swap1j];
        temp[swap1i][swap1j] = temp[swap2i][swap2j];
        temp[swap2i][swap2j] = tmp;
        return new Board(temp);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] a = new int[][]{{1,2, 3},{4,5,6},{7,0,8}};
//        int[][] a = new int[][]{{1, 2}, {3, 0}};

        Board bd = new Board(a);
        StdOut.print(bd.dimension() + "\n");
        StdOut.print(bd.toString());
        StdOut.print("\n");
        StdOut.print(bd.hamming() + "\n");
        StdOut.print(bd.manhattan() + "\n");
        StdOut.print(bd.isGoal() + "\n");

        for (Board i : bd.neighbors()) {
            StdOut.print("\n\n" + i.toString()+ "\n\n");
        }

        Board twin = bd.twin();
        StdOut.print(twin.toString());
    }

}
