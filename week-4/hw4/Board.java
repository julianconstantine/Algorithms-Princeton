import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;

public class Board {
    public int[][] board;       // Array containing the current board
    public int[][] goal;        // Array containing the goal board
    public static int n;        // Dimension of board
    // public static int index;

    public Board(int[][] blocks) {
        board = blocks;
        n = blocks[0].length;

        // // Find the location of the zero index
        // for (int i = 0; i < n; i++) {
        //     for (int j = 0; j < n; j++) {
        //         if (blocks[i][j] == 0) {
        //             index = i*n + j;
        //             break;
        //         }
        //     }
        // }

        // Initialize the goal board
        // Note: Do NOT do int[][]goal = new goal[n][n]. This will create a new
        // array goal that is distinct from the array called goal that is part
        // of the class
        goal = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                goal[i][j] = i*n + j + 1;
            }
        }

        // Set the last position of the goal board to 0 (not n^2)
        goal[n-1][n-1] = 0;
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        int sum = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != goal[i][j]) sum++;
            }
        }

        return sum;
    }

    public int manhattan() {
        int sum = 0;
        int ii, jj;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0) {
                    ii = (board[i][j] - 1)/ n;
                    jj = board[i][j] - n*ii- 1;

                    // Print out the board item board[i][j], its indices i and j, and
                    // its "correct" indices in the goal board ii and jj
                    // System.out.println(board[i][j] + ": " + i + " " + ii + " " + j + " " + jj);

                    sum = sum + Math.abs(ii - i) + Math.abs(jj - j);
                }
            }
        }

        return sum;
    }

    public boolean isGoal() {
        return equals(goal);
    }

    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }

        Board yBoard = (Board) y;

        if (n != yBoard.dimension()) {
            // Must have the same dimension
            return false;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // If any two elements are not equal, stop function execution and
                // return false
                if (board[i][j] != yBoard.board[i][j]) return false;
            }
        }

        // If all elements compared are equal, return true
        return true;
    }


    /*************************************
     * Helper functions
     *************************************/

    /**
     * Print out the entire board with each entry in a fieldwidth of 5
     */
     public String toString() {
         StringBuilder s = new StringBuilder();
         s.append(n + "\n");
         for (int i = 0; i < n; i++) {
             for (int j = 0; j < n; j++) {
                 s.append(String.format("%2d ", board[i][j]));
             }
             s.append("\n");
         }
         return s.toString();
     }

    /**
     * Print out the goal board with each entry in a fieldwidth of 5
     */
     private String goalToString() {
         StringBuilder s = new StringBuilder();
         s.append(n + "\n");
         for (int i = 0; i < n; i++) {
             for (int j = 0; j < n; j++) {
                 s.append(String.format("%2d ", goal[i][j]));
             }
             s.append("\n");
         }
         return s.toString();
     }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();

        int[][] blocks = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = in.readInt();
            }
        }

        Board initial = new Board(blocks);
        Board goal = new Board(initial.goal);

        // Test out the equals() method
        System.out.println("Board equals itself: " + initial.equals(initial));
        System.out.println("Board equals goal: " + initial.equals(goal));
        System.out.println("Board equals null: " + initial.equals(null));
        System.out.println();

        // Print out the initial board
        System.out.println("Initial board:");
        System.out.println(initial.toString());
        System.out.println();

        // Print out the goal board
        System.out.println("Goal board:");
        System.out.println(initial.goalToString());
        System.out.println();

        // Print out the Hamming and Manhattan distance of this board
        System.out.println("Hamming distance: " + initial.hamming());
        System.out.println("Manhattan distance: " + initial.manhattan());
        System.out.println();
    }
}
