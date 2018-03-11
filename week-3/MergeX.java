/******************************************************************************
 *  Compilation:  javac MergeX.java
 *  Execution:    java MergeX < input.txt
 *  Dependencies: StdOut.java StdIn.java
 *  Data files:   http://algs4.cs.princeton.edu/22mergesort/tiny.txt
 *                http://algs4.cs.princeton.edu/22mergesort/words3.txt
 *
 *  Sorts a sequence of strings from standard input using an
 *  optimized version of mergesort.
 *
 *  % more tiny.txt
 *  S O R T E X A M P L E
 *
 *  % java MergeX < tiny.txt
 *  A E E L M O P R S T X                 [ one string per line ]
 *
 *  % more words3.txt
 *  bed bug dad yes zoo ... all bad yet
 *
 *  % java MergeX < words3.txt
 *  all bad bed bug dad ... yes yet zoo    [ one string per line ]
 *
 ******************************************************************************/

 import java.util.Comparator;
 import edu.princeton.cs.algs4.StdIn;

 public class MergeX {
     // IMPROVEMENT #1: Use insertion sort for small subarrays
     private static final int CUTOFF = 7;  // Cutoff to insertion Sorts

     // This class should not be instantiated
    private MergeX() {}

    private static void merge(Comparable[] src, Comparable[] dst, int low, int mid, int high) {
        // Precondition: src[low ... mid] and src[mid+1 ... high] are sorted subarrays
        assert isSorted(src, low, mid);
        assert isSorted(src, mid+1, high);

        int i = low;
        int j = mid + 1;

        for (int k = low; k <= high; k++) {
            if (i > mid)                    dst[k] = src[j++];
            else if (j > high)              dst[k] = src[i++];
            else if (less(src[j], src[i]))  dst[k] = src[j++];
            else                            dst[k] = src[i++];
        }

        // Postcondition: dst[low ... high] is a sorted subarray
        assert isSorted(dst, low, high);
    }

    private static void sort(Comparable[] src, Comparable[] dst, int low, int high) {
        // if (high <= low) return;

        // IMPROVEMENT #1: Use insertion sort for small subarrays
        if (high <= low + CUTOFF) {
            insertionSort(dst, low, high);
            return;
        }

        int mid = low + (high - low) / 2;

        sort(dst, src, low, mid);
        sort(dst, src, mid+1, high);

        // IMPROVEMENT #2: Test whether array is already in order
        // if (!less(src[mid+1], src[mid])) {
        //     for (int j = low; i <= high; i++) dst[i] = src[i];
        //     return;
        // }

        // Using System.arraycopy() is a bit faster than the above loop
        if (!less(src[mid+1], src[mid])) {
            System.arraycopy(src, low, dst, low, high- low + 1);
            return;
        }

        merge(src, dst, low, mid, high);
    }

    public static void sort(Comparable[] a) {
        Comparable[] aux = a.clone();
        sort(aux, a, 0, a.length-1);

        assert isSorted(a);
    }

    // Sort from a[low] to a[high] using insertion sort
    private static void insertionSort(Comparable[] a, int low, int high) {
        for (int i =low; i <= high; i++) {
            for (int j = i; j > low && less(a[j], a[j-1]); j--) {
                exch(a, j, j-1);
            }
        }
    }

    /*
     * UTILITY METHODS
     */

     // Exchange a[i] and a[j]
     private static void exch(Object[] a, int i, int j) {
         Object swap = a[i];
         a[i] = a[j];
         a[j] = swap;
     }

     // Is a[i] < a[j]?
     private static boolean less(Comparable a, Comparable b) {
         return a.compareTo(b) < 0;
     }

     // Is a[i] < a[j]?
     private static boolean less(Object a, Object b, Comparator comparator) {
         return comparator.compare(a, b) < 0;
     }

     /*
      * VERSION USING COMPARATORS
      */

      public static void sort(Object[] a, Comparator comparator) {
          Object[] aux = a.clone();
          sort(aux, a, 0, a.length-1, comparator);
          assert isSorted(a, comparator);
      }

      private static void merge(Object[] src, Object dst[], int low, int mid, int high, Comparator comparator) {
          // Precondition: src[low ... mid] and src[mid+1 ... high] are sorted subarrays
          assert isSorted(src, low, mid, comparator);
          assert isSorted(src, mid+1, high, comparator);

          int i = low;
          int j = mid+1;

          for (int k = low; k <= high; k++) {
              if      (i > mid)                          dst[k] = src[i++];
              else if (j > high)                         dst[k] = src[i++];
              else if (less(src[j], src[i], comparator)) dst[k] = src[j++];
              else                                       dst[k] = src[i++];
          }

          // Postcondition: dst[low ... high] is a sorted subarrays
          assert isSorted(dst, low, high, comparator);
      }

      private static void sort(Object[] src, Object[] dst, int low, int high, Comparator comparator) {
          // if (high <= low) return;
          if (high <= low + CUTOFF) {
              insertionSort(dst, low, high, comparator);
              return;
          }

          int mid = low + (high - low) / 2;

          sort(dst, src, low, mid, comparator);
          sort(dst, src, mid+1, high, comparator);

          if (!less(src[mid+1], src[mid], comparator)) {
              System.arraycopy(src, low, dst, low, high - low + 1);
              return;
          }

          merge(src, dst, low, mid, high, comparator);
      }

      private static void insertionSort(Object[] a, int low, int high, Comparator comparator) {
          for (int i = low; i <= high; i++) {
              for (int j = i; j > low && less(a[j], a[j-1], comparator); j--) {
                  exch(a, j, j-1);
              }
          }
      }

      /*
       * CHECK IF ARRAY IS SORTED
       */

       // For Comparables
       private static boolean isSorted(Comparable[] a) {
           return isSorted(a, 0, a.length-1);
       }

       private static boolean isSorted(Comparable[] a, int low, int high) {
           for (int i = low + 1; i <= high; i++) {
               if (less(a[i], a[i-1])) return false;
           }

           return true;
       }

       // For Comparators
       private static boolean isSorted(Object[] a, Comparator comparator) {
           return isSorted(a, 0, a.length-1, comparator);
       }

       private static boolean isSorted(Object[] a, int low, int high, Comparator comparator) {
           for (int i = low + 1; i <= high; i++) {
               if (less(a[i], a[i-1], comparator)) return false;
           }

           return true;
       }

       private static void show(Object[] a) {
           for (int i = 0; i < a.length; i++) {
               System.out.println(a[i]);
           }
       }

       public static void main(String[] args) {
           String[] a = StdIn.readAllStrings();
           MergeX.sort(a);
           show(a);
       }
 }
