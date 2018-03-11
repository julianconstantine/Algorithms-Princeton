/******************************************************************************
 *  Compilation:  javac Quick3way.java
 *  Execution:    java Quick3way < input.txt
 *  Dependencies: StdOut.java StdIn.java
 *  Data files:   http://algs4.cs.princeton.edu/23quicksort/tiny.txt
 *                http://algs4.cs.princeton.edu/23quicksort/words3.txt
 *
 *  Sorts a sequence of strings from standard input using 3-way quicksort.
 *
 *  % more tiny.txt
 *  S O R T E X A M P L E
 *
 *  % java Quick3way < tiny.txt
 *  A E E L M O P R S T X                 [ one string per line ]
 *
 *  % more words3.txt
 *  bed bug dad yes zoo ... all bad yet
 *
 *  % java Quick3way < words3.txt
 *  all bad bed bug dad ... yes yet zoo    [ one string per line ]
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;

public class Quick3way {
    // This class should not be instantiated
    // Need to manually define constructor as PRIVATE if you dont want the class to instantiated
    private Quick3way() { }

    // Main (public) sorting subroutine (sorts entire array)
    public static void sort(Comparable[] a) {
        // Shuffle the array first
        StdRandom.shuffle(a);

        // Invote sorting subroutine on entire array
        sort(a, 0, a.length - 1);

        // Test if sorted
        assert isSorted(a);
    }

    // Hidden (private) sorting subroutine (sorts portion of array)
    private static void sort(Comparable[] a, int low, int high) {
        if (high < low) return;

        // Initialize lt and gt pointers to low and high respectively
        int lt = low, gt = high;

        // Partioning item (aka "pivot")
        Comparable v = a[low];

        // Initialize pointer i to low (left end of subarray)
        int i = low;

        while (i <= gt) {
            int cmp = a[i].compareTo(v);

            // If current element less than pivot, swap current element i with lt and increment i and lt
            if (cmp < 0) exch(a, lt++, i++);

            // If current element greater than pivot, swap current element with gt and decrement gt
            else if (cmp > 0) exch(a, i, gt--);

            // Else increment i
            else i++;
        }

        // Sort left half of subarray
        sort(a, low, lt-1);

        // Sort right half of subarray
        sort(a, gt+1, high);

        // Check if entire subarray is sorted
        assert isSorted(a, low, high);
    }

    /*****************************
     *  HELPER SORTING FUNCTIONS
     *****************************/

     // Want these functions to be PRIVATE because the user is not supposed to access them

     // Is v less than w?
     private static boolean less(Comparable v, Comparable w) {
         return v.compareTo(w) < 0;
     }

     // Exchange a[i] and a[j]
     private static void exch(Object[] a, int i, int j) {
         Object swap = a[i];
         a[i] = a[j];
         a[j] = swap;
     }

     /***************
      *  ASSERTIONS
      ***************/

      // Should also be private

      // Check if entire array is sorted
      private static boolean isSorted(Comparable[] a) {
          return isSorted(a, 0, a.length - 1);
      }

      // Check if subarray is sorted
      private static boolean isSorted(Comparable[] a, int low, int high) {
          for (int i = low + 1; i <= high; i++) {
              if (less(a[i], a[i-1])) return false;
          }

          return true;
      }

      // Print array to standard output
      private static void show(Comparable[] a) {
          for (int i = 0 ; i < a.length; i++) {
              System.out.println(a[i]);
          }
      }

      /**
       * Reads in a sequence of strings from standard input, 3-way quicksorts them, then prints to standard output in ascending order
       */
       public static void main(String[] args) {
           String[] a = StdIn.readAllStrings();
           Quick3way.sort(a);
           show(a);
       }

}
