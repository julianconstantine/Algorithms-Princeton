/******************************************************************************
 *  Compilation:  javac Merge.java
 *  Execution:    java Merge < input.txt
 *  Dependencies: StdOut.java StdIn.java
 *  Data files:   http://algs4.cs.princeton.edu/22mergesort/tiny.txt
 *                http://algs4.cs.princeton.edu/22mergesort/words3.txt
 *
 *  Sorts a sequence of strings from standard input using mergesort.
 *
 *  % more tiny.txt
 *  S O R T E X A M P L E
 *
 *  % java Merge < tiny.txt
 *  A E E L M O P R S T X                 [ one string per line ]
 *
 *  % more words3.txt
 *  bed bug dad yes zoo ... all bad yet
 *
 *  % java Merge < words3.txt
 *  all bad bed bug dad ... yes yet zoo    [ one string per line ]
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;

public class Merge {
    // This class should not be instantiated
    private Merge() {}

    private static void merge(Comparable[] a, Comparable[] aux, int low, int mid, int high) {
        assert isSorted(a, low, mid);   // Precondition: a[low:mid] is sorted
        assert isSorted(a, mid+1, high);   // Precondition: a[mid+1:high] is sorted

        // Copy over a[low:high+1] to the auxiliary array
        for (int k = low; k <= high; k++) aux[k] = a[k];

        // Initialize indices i and j to low and mid+1 respectively
        int i = low, j = mid+1;

        // Merge the two sorted subarrays
        for (int k = low; k <= high; k++) {
            if (i > mid) {
                // If i pointer is exhausted, copy from right half and increment j
                a[k] = aux[j++];
            } else if (j > high) {
                // If j pointer is exhausted, copy from left half and increment i
                a[k] = aux[i++];
            } else if (less(aux[j], aux[i])) {
                // If neither pointers are exhausted, copy from whichever half has the smallest current element
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }

        // Postcondition: a[low ... high] is sorted
        assert isSorted(a, low, high);
    }

    // Recursive sorting subroutine
    private static void sort(Comparable[] a, Comparable[] aux, int low, int high) {
        if (high <= low) return;

        int mid = low + (high - low) / 2;

        // Sort left half
        sort(a, aux, low, mid);

        // Sort right half
        sort(a, aux, mid+1, high);

        // If largest element of first half is less than smallest element of second half, do not need to merge
        // if (!less(a[mid+1], a[mid])) return;

        // Merge halves together
        merge(a, aux, low, mid, high);
    }

    // Public sort method
    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);

        assert isSorted(a);
    }

    /*
       HELPFER SORTING FUNCTIONS
    */

    // Check if v is less than w
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    /*
        ASSERTIONS
    */

    // Check if entire array is sorted
    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length-1);
    }

    // Check if subarray a[low ... high] is sorted
    private static boolean isSorted(Comparable[] a, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            if (less(a[i], a[i-1])) return false;
        }

        return true;
    }

    /*
        INDEX MERGESORT
    */

    // Stably merge a[lo ... mid] with a[mid+1 ... high] using aux[low ... high]
    private static void merge(Comparable[] a, int[] index, int[] aux, int low, int mid, int high) {
        // Copy to aux[]
        for (int k = low; k <= high; k++) {
            aux[k] = index[k];
        }

        // Merge back to a[]
        int i = low;
        int j = mid + 1;

        for (int k = low; k <= high; k++) {
            if (i > mid) {
                index[k] = aux[j++];
            } else if (j > high) {
                index[k] = aux[i++];
            } else if (less(a[aux[j]], a[aux[i]])) {
                index[k] = aux[j++];
            } else {
                index[k] = aux[i++];
            }
        }
    }

    public static int[] indexSort(Comparable[] a) {
        int n = a.length;
        int[] index = new int[n];

        for (int i = 0; i < n; i++) {
            index[i] = i;
        }

        int[] aux = new int[n];

        sort(a, index, aux, 0, n-1);

        return index;
    }

    private static void sort(Comparable[] a, int[] index, int[] aux, int low, int high) {
        if (high <= low) return;

        int mid = low + (high - low) / 2;

        sort(a, index, aux, low, mid);
        sort(a, index, aux, mid + 1, high);
        merge(a, index, aux, low, mid, high);
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        Merge.sort(a);
        show(a);
    }

}
