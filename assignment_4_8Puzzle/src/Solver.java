import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;


public final class Solver {
    private searchNode lastNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("?");

        MinPQ<searchNode> minPQ = new MinPQ<searchNode>();
        MinPQ<searchNode> minPQTwin = new MinPQ<searchNode>();

        searchNode firstStep = new searchNode(0, initial.manhattan(), null, initial);
        Board initialTwin = initial.twin();
        searchNode firstStepTwin = new searchNode(0, initialTwin.manhattan(), null, initialTwin);

        minPQ.insert(firstStep);
        minPQTwin.insert(firstStepTwin);

        searchNode nextPick = minPQ.delMin();
        searchNode nextPickTwin = minPQTwin.delMin();
        lastNode = nextPick;

        while (!nextPick.data.isGoal() && !nextPickTwin.data.isGoal()) {
            nextPick = perstepAStar(minPQ, nextPick);
            lastNode = nextPick;
            nextPickTwin = perstepAStar(minPQTwin, nextPickTwin);
        }
    }

    private searchNode perstepAStar(MinPQ<searchNode> minPQ, searchNode current) {
        searchNode next;
        for (Board bd : current.data.neighbors()) {
            if (current.moves == 0 ){
                minPQ.insert(new searchNode(current.moves + 1, bd.manhattan(), current, bd));

            }
            else if (!bd.equals(current.previous.data) ) {
                minPQ.insert(new searchNode(current.moves + 1, bd.manhattan(), current, bd));
            }
        }
        next = minPQ.delMin();
        return next;
    }


    // searchNode object,
    private class searchNode implements Comparable<searchNode>
    {
        private final int  moves;
        private final int manhattanDistance;
        private final searchNode previous;
        private final Board data;
        public searchNode(int moves, int manhattanDistance, searchNode previous, Board data) {
            this.moves = moves;
            this.manhattanDistance = manhattanDistance;
            this.previous = previous;
            this.data = data;
        }
        public int compareTo(searchNode that) {
            int result = this.moves + this.manhattanDistance - that.moves - that.manhattanDistance;
            if (result < 0) return -1;
            else if (result > 0) return 1;
            else return 0;
        }
    }


    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return (lastNode.data.isGoal());
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        else return lastNode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        Stack<Board> stack = new Stack<Board>();
        searchNode current = lastNode;
        while (current != null) {
            stack.push(current.data);
            current = current.previous;
        }
        return stack;
    }

    // test client (see below)
    public static void main(String[] args){


//        int[][] a = new int[][]{{8, 1, 3},{4,2,5},{7,0,6}};
        int[][] a = new int[][]{{1, 0}, {2, 3}};

        Board board = new Board(a);
        Solver solver = new Solver(board);
        StdOut.println(solver.moves());
        StdOut.println(solver.isSolvable());
        for (Board bd : solver.solution()) {
            StdOut.print("\n" + bd.toString());
        }

        // create initial board from file

        /*
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

         */
    }

}