/******************************************************************************
 *  Compilation:  javac MaxPQ.java
 *  Execution:    java MaxPQ < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/24pq/tinyPQ.txt
 *
 *  Generic max priority queue implementation with a binary heap.
 *  Can be used with a comparator instead of the natural order,
 *  but the generic Key type must still be Comparable.
 *
 *  % java MaxPQ < tinyPQ.txt
 *  Q X P (6 left on pq)
 *
 *  We use a one-based array to simplify parent and child calculations.
 *
 *  Can be optimized by replacing full exchanges with half exchanges
 *  (ala insertion sort).
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MaxPQ<Key> implements Iterable<Key> {
    private Key[] pq;  // Store n items at indices 1 through n
    private int n;  // Number of items in priority queue
    private Comparator<Key> comparator;  // Optional comparator

    /**
     * Initializes an empty peiority queue with the given initial capacity
     *
     * @param   initCapacity, the initial capacity of this priority queue
     */
    public MaxPQ(int initCapacity) {
        pq = (Key[]) new Object[initCapacity+1];
        n = 0;
    }

    /**
     * Initializes an empty priority queue
     */
    public MaxPQ() {
        this(1);
    }

    /**
     * Initializes an empty priority queue with the given initial capacity using
     * the given comparator
     *
     * @param   initCapacity, the initial capacity of this priority queue
     * @param   comparator, the order in which to compare the keys
     */
    public MaxPQ(int initCapacity, Comparator<Key> comparator) {
        this.comparator = comparator;
        pq = (Key[]) new Object[initCapacity+1];
        n = 0;
    }

    /**
     * Initializes an empty priority queue using the given comparator
     *
     * @param   comparator, the order in which to compare the keys
     */
    public MaxPQ(Comparator<Key> comparator) {
        this(1, comparator);
    }

    /**
     * Initializes a priority queue from the array of keys.
     * Takes time proportional to the number of keys, using sink-based heap construction.
     *
     * @param   keys, the array of keys
     */
    public MaxPQ(Key[] keys) {
        n = keys.length;
        pq = (Key[]) new Object[n + 1];

        for (int i = 0; i < n; i++) {
            pq[i+1] = keys[i];
        }

        for (int k = n/2; k >= 1; k--) {
            sink(k);
        }

        assert isMaxHeap();
    }

    /**
     * Returns true if the priority queue is empty
     *
     * @return  {@code true} if this priority queue is empty
     *          {@code false} otherwise
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Returns the number of keys in the priority queue
     *
     * @returns the number of keys in the priority queue
     */
    public int size() {
        return n;
    }

    /**
     * Returns the largest key in the priority queue
     *
     * @returns the largest key in the priority queue
     * @throws  NoSuchElementException if the priority queue is empty
     */
    public Key max() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }

        return pq[1];
    }

    // Helper function to double the size of the heap array
    private void resize(int capacity) {
        assert capacity > n;

        Key[] temp = (Key[]) new Object[capacity];

        for (int i = 1; i <= n; i++) {
            temp[i] = pq[i];
        }

        pq = temp;
    }

    /**
     * Adds a new key to the priority queue
     *
     * @param   x, the new key to add
     */
    public void insert(Key x) {
        // Double size of array if necessary
        if (n >= pq.length - 1) {
            resize(2 * pq.length);
        }

        // Add x, and percolate it up to maintain heap invariant
        pq[++n] = x;
        swim(n);

        assert isMaxHeap();
    }

    /**
     * Removes and returns the largest key on the priority queue
     *
     * @returns the largest key in the peiority queue
     * @throws  NoSuchElementException if the priority queue is empty
     */
    public Key delMax() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }

        // Largest item (root element) in priority queue
        Key max = pq[1];

        // Swap out last element (position n) with first element (in position 1)
        exch(1, n--);

        // Sink the new first element into its proper place
        sink(1);

        // Delete item in the last position of the array
        pq[n+1] = null;

        if ((n > 0) && (n == (pq.length - 1) / 4)) {
            resize(pq.length / 2);
        }

        assert isMaxHeap();

        return max;
    }

    /************************************************************************
     * Helper functions to restore the heap invariant
     ***********************************************************************/

    private void swim(int k) {
        while (k > 1 && less(k/2, k)) {
            // Exchange item k with its parent k/2
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;

            if (j < n && less(j, j+1)) {
                j++;
            }

            if (!less(k, j)) {
                break;
            }

            exch(k, j);
            k = j;
        }
    }

    /*************************************************************
     * Helper functions for compares and swaps
     *************************************************************/

     private boolean less(int i, int j) {
         if (comparator == null) {
             return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
         } else {
             return comparator.compare(pq[i], pq[j]) < 0;
         }
     }

     private void exch(int i, int j) {
         Key swap = pq[i];
         pq[i] = pq[j];
         pq[j] = swap;
     }

     // Test if pq[1...N] is a max-heap
     private boolean isMaxHeap() {
         return isMaxHeap(1);
     }

     // Is subtree of pq[1...n] rooted at k a max-heap
     private boolean isMaxHeap(int k) {
         if (k > n) return true;
         int left = 2*k;
         int right = 2*k + 1;

         if (left <= n && less(k, left)) return false;
         if (right <= n && less(k, right)) return false;

         return isMaxHeap(left) && isMaxHeap(right);
     }

     /******************************************************
      * Iterator
      ******************************************************/

     public Iterator<Key> iterator() {
         return new HeapIterator();
     }

     private class HeapIterator implements Iterator<Key> {
         // Create a new priority queue
         private MaxPQ<Key> copy;

         // Add all items to copy of heap
         // Note: This takes linear time since already in heap order so no keys move
         public HeapIterator() {
             if (comparator == null) {
                 copy = new MaxPQ<Key>(size());
             } else {
                 copy = new MaxPQ<Key>(size(), comparator);
             }

             for (int i = 1; i <= n; i++) {
                 copy.insert(pq[i]);
             }
         }

         public boolean hasNext() {
             return !copy.isEmpty();
         }

         public void remove() {
             throw new UnsupportedOperationException();
         }

         public Key next() {
             if (!hasNext()) {
                 throw new NoSuchElementException();
             }

             return copy.delMax();
         }
     }

     /**
      * Unit tests {@code MaxPQ} data type
      *
      * @param  args, the command-line arguments
      */
     public static void main(String[] args) {
         MaxPQ<String> pq = new MaxPQ<String>();

         while (!StdIn.isEmpty()) {
             String item = StdIn.readString();

             if (!item.equals("-")) {
                 pq.insert(item);
             } else if (!pq.isEmpty()) {
                 System.out.print(pq.delMax() + " ");
             }
         }

         System.out.println("(" + pq.size() + " left on pq)");
     }
}
