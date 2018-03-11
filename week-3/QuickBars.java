/******************************************************************************
 *  Compilation:  javac QuickBars.java
 *  Execution:    java QuickBars m n
 *  Dependencies: StdDraw.java
 *
 *  Sort n random real numbers between 0 and 1 using quicksort with
 *  cutoff to insertion sort and median-of-3 partitioning.
 *
 *  Visualize the results by ploting bars with heights proportional
 *  to the values.
 *
 *  % java QuickBars 1000 75
 *
 *  Comments
 *  --------
 *   - if image is too large, it may not display properly but you can
 *     still save it to a file
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class QuickBars {
    private static int rows;
    private static int row = 0;
    private static final int VERTICAL = 70;
    private static final int CUTOFF = 8;

    // Partition the array
    private static int partition(double[] a, int low, int high) {
        int i = low;
        int j = high + 1;

        // Partitioning item
        double v = a[low];

        while (true) {
            // Find item on low to swap
            while (less(a[++i], v)) {
                if (i == high) break;
            }

            // Find item on high to swap
            while (less(v, a[--j])) {
                if (j == low) break;
            }

            // Check if pointers cross
            if (i >= j) break;

            // Swap elements a[i] and a[j]
            exch(a, i, j);
        }

        // Swap elements v and a[j]
        exch(a, low, j);

        return j;
        }

        public static void sort(double[] a) {
        show(a, 0, 0, -1, a.length-1);
        sort(a, 0, a.length-1);
        show(a, 0, 0, -1, a.length-1);
    }

    // Quicksort subarray from a[low] to a[high]
    private static void sort(double[] a, int low, int high) {
        // Cutoff to insertion sort
        int n = high - low + 1;

        if (n <= CUTOFF) {
            insertionSort(a, low, high);
            return;
        }

        int m = median3(a, low, low + n/2, high);
        exch(a, m, low);

        int j = partition(a, low, high);
        show(a, low, j, j, high);
        sort(a, low, j-1);
        sort(a, j+1, high);

    }

    // Insertion sort the subarray from a[low] to a[high]
    private static void insertionSort(double[] a, int low, int high) {
        for (int i = low; i <= high; i++) {
            for (int j = i; j > low && less(a[j], a[j-1]); j--) {
                exch(a, j, j-1);
            }
        }
    }

    // Return the index of the median element of {a[i], a[j], a[k]}
    private static int median3(double[] a, int i, int j, int k) {
        // COND ? EXPR1 : EXPR2 is shorthand for if (COND) EXPR1 else EXPR2
        return (less(a[i], a[j]) ?
               (less(a[j], a[k]) ? j : less(a[i], a[k]) ? k : i) :
               (less(a[k], a[j]) ? j : less(a[k], a[i]) ? k : i));
    }

    /*
    * SORTING HELPER FUNCTIONS
    */

    // Test if v < w
    private static boolean less(double v, double w) {
        return v < w;
    }

    // Exchange a[i] and a[j]
    private static void exch(double[] a, int i, int j) {
        double t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void show(double[] a, int low, int lt, int gt, int high) {
        double y = rows - row + 1;

        for (int k = 0; k < a.length; k++) {
            if (k < low) {
                StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
            } else if (k > high) {
                StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
            } else if (k >= lt && k <= gt) {
                StdDraw.setPenColor(StdDraw.BOOK_RED);
            } else {
                StdDraw.setPenColor(StdDraw.BLACK);
            }

            StdDraw.filledRectangle(k, y + a[k]*0.25, 0.25, a[k]*0.25);
        }

        row++;
    }

    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);

        double[] a = new double[n];
        double[] b = new double[n];

        for (int i = 0; i < n; i++) {
            a[i] = (1 + StdRandom.uniform(m) / (double) m);
            b[i] = a[i];
        }

        StdDraw.enableDoubleBuffering();

        // Precompute the number of rows
        rows = 0;
        sort(b);
        rows = row;
        row = 0;
        StdDraw.clear();

        StdDraw.setCanvasSize(800, rows*VERTICAL);
        StdDraw.show();
        StdDraw.square(0.5, 0.5, 0.5);
        StdDraw.setXscale(-1, n);
        StdDraw.setYscale(-0.5, rows);
        StdDraw.show();
        sort(a);
        StdDraw.show();
    }
}
