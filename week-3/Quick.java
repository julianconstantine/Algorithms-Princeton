/******************************************************************************
 *  Compilation:  javac Quick.java
 *  Execution:    java Quick < input.txt
 *  Dependencies: StdOut.java StdIn.java
 *  Data files:   http://algs4.cs.princeton.edu/23quicksort/tiny.txt
 *                http://algs4.cs.princeton.edu/23quicksort/words3.txt
 *
 *  Sorts a sequence of strings from standard input using quicksort.
 *
 *  % more tiny.txt
 *  S O R T E X A M P L E
 *
 *  % java Quick < tiny.txt
 *  A E E L M O P R S T X                 [ one string per line ]
 *
 *  % more words3.txt
 *  bed bug dad yes zoo ... all bad yet
 *
 *  % java Quick < words3.txt
 *  all bad bed bug dad ... yes yet zoo    [ one string per line ]
 *
 *
 *  Remark: For a type-safe version that uses static generics, see
 *
 *    http://algs4.cs.princeton.edu/23quicksort/QuickPedantic.java
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;

public class Quick {
    // This class should not be instantiated
    private Quick() {}

    private static int partition(Comparable[] a, int low, int high) {
        int i = low;
        int j = high + 1;

        // Partitioning item (aka "pivot" element?)
        Comparable v = a[low];

        while (true) {
            // Find item on left to swap
            while (less(a[++i], v)) {
                if (i == high) break;
            }

            // Find item on right to swap
            while (less(v, a[--j])) {
                if (j == low) break;
            }

            // Check if pointers cross
            if (i >= j) break;

            // Swap a[i] and a[j]
            exch(a, i, j);
        }

        // Swap with partitioning item
        exch(a, low, j);

        // Return index of item known to be in place
        return j;
    }

    // Public subroutine to sort the entire array in ascending order
    public static void sort(Comparable[] a) {
        // Shuffle deck to get good performance (THIS IS REQUIRED)
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);

        // Check if array is sorted
        assert isSorted(a);
    }

    // Private subroutine to sort some subarray in ascending order
    public static void sort(Comparable[] a, int low, int high) {
        if (high <= low) return;

        int j = partition(a, low, high);

        sort(a, low, j-1);
        sort(a, j+1, high);
    }

    // Return kth smallest item in the array
    public static Comparable select(Comparable[] a, int k) {
        if (k < 0 || k >= a.length) {
            throw new IndexOutOfBoundsException("selected element out of bounds");
        }

        StdRandom.shuffle(a);

        int low = 0;
        int high = a.length - 1;

        while (high > low) {
            int j = partition(a, low, high);

            if (j < k) {
                low = j + 1;
            } else if (j > k) {
                high = j - 1;
            } else {
                return a[k];
            }
        }

        return a[k];
    }

    /*
     *  HELPER SORTING FUNCTIONS
    */

    // We must implement these ourselves each time wqe use them

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    /*
     *  ASSERTIONS
     */

     // Check if entire array is sorted
     private static boolean isSorted(Comparable[] a) {
         return isSorted(a, 0, a.length-1);
     }

     // Check if some subarray is sorted
     private static boolean isSorted(Comparable[] a, int low, int high) {
         for (int i = low + 1; i <= high; i++) {
             if (less(a[i], a[i-1])) return false;
         }

         return true;
     }

     // Print array to standard output
     private static void show(Comparable[] a) {
         for (int i = 0; i < a.length; i++) {
             System.out.println(a[i]);
         }
     }

     // Reads in a sequence of strings from standard input, quicksorts them, then prints to standard output in ascending order
     public static void main(String[] args) {
         // Read in all standard input to the array a
         String[] a = StdIn.readAllStrings();

         // Quicksort the array
         Quick.sort(a);

         // Display the results
         show(a);

         // Shuffle the array
         StdRandom.shuffle(a);

         // Display the resultsd again using select
         System.out.println();

         for (int i = 0; i < a.length; i++) {
             // Get ith smallest element and print it
             String ith = (String) Quick.select(a, i);
             System.out.println(ith);
         }


     }
}
