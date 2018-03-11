/******************************************************************************
 *  Compilation:  javac UnorderedArrayMaxPQ.java
 *  Execution:    java UnorderedArrayMaxPQ
 *  Dependencies: StdOut.java
 *
 *  Priority queue implementation with an unsorted array.
 *
 *  Limitations
 *  -----------
 *   - no array resizing
 *   - does not check for overflow or underflow.
 *
 ******************************************************************************/

public class UnorderedArrayMaxPQ<Key extends Comparable<Key>> {
    private Key[] pq;  // Elements of priority queue
    private int n ;  // Number of elements in priority queue

    // Set initial size of heap to hold a specified size elements
    // NOTE: This is the "cheat" version where the client must provide the capacity
    public UnorderedArrayMaxPQ(int capacity) {
        // Recall: No generic array creation
        pq = (Key[]) new Comparable[capacity];
        n = 0;
    }

    public boolean isEmpty()    { return n == 0; }
    public int size()            { return n; }
    public void insert(Key x)   { pq[n++] = x; }

    public Key delMax() {
        int max = 0;
        for (int i = 1; i < n; i++) {
            if (less(max, i)) max = i;
        }

        // Swap out the maximum with the last element, then remove and return it
        exch(max, n-1);

        return pq[--n];
    }

    // HELPER FUNCTIONS

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    // TEST ROUTINE

    public static void main(String[] args) {
        UnorderedArrayMaxPQ<String> pq = new UnorderedArrayMaxPQ<String>(10);
        pq.insert("this");
        pq.insert("is");
        pq.insert("a");
        pq.insert("test");

        while (!pq.isEmpty()) {
            System.out.println(pq.delMax());
        }
    }

}
