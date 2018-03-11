/******************************************************************************
 *  Compilation:  javac BinarySearchST.java
 *  Execution:    java BinarySearchST
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/31elementary/tinyST.txt
 *
 *  Symbol table implementation with binary search in an ordered array.
 *
 *  % more tinyST.txt
 *  S E A R C H E X A M P L E
 *
 *  % java BinarySearchST < tinyST.txt
 *  A 8
 *  C 4
 *  E 12
 *  H 5
 *  L 11
 *  M 9
 *  P 10
 *  R 3
 *  S 0
 *  X 7
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Queue;  // Import our Queue implementation

import java.util.NoSuchElementException;

public class BinarySearchST<Key extends Comparable<Key>, Value> {
    private static final int INIT_CAPACITY = 2;
    private Key[] keys;
    private Value[] vals;
    private int n = 0;

    /**
     * Initializes an empty symbol table
     */
    public BinarySearchST() {
        this(INIT_CAPACITY);
    }

    /**
     * Initializes an empty symbol table with the specified initial capacity.
     * @param capacity the maximum capacity
     */
     public BinarySearchST(int capacity) {
         keys = (Key[]) new Comparable[capacity];
         vals = (Value[]) new Object[capacity];
     }

     // Resizes the underlying arrays
     private void resize(int capacity) {
         assert capacity >= 0;

         Key[] tempx = (Key[]) new Comparable[capacity];
         Value[] tempv = (Value[]) new Object[capacity];

         for (int i = 0; i < n; i++) {
             tempk[i] = keys[i];
             tempv[i] = vals[i];
         }

         vals = tempv;
         keys = tempk;
     }

     /**
      * Returns the number of key-value pairs in this symbol table.
      *
      * @return the number of key-value pairs in this symbol table
      */
     public int size() {
         return n;
     }

     /**
      * Returns true if this symbol table is empty.
      *
      * @return {@code true} if this symbol table is empty;
      *         {@code false} otherwise
      */
     public boolean isEmpty() {
         return size() == 0;
     }

     /**
      *  Does this symbol table contain the given key?
      *
      * @param  key the key
      * @return {@code true} if this symbol table contains {@code key} and
      *         {@code false} otherwise
      * @throws NullPointerException if {@code key} is {@code null}
      */
     public boolean contains(Key key) {
         if (key == null) {
             throw new NullPointerException("argument to contains() is null");
         }

         return get(key) != null;
     }

    /**
     * Returns the value associated with the given key in this symbol table.
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in the symbol table
     *         and {@code null} if the key is not in the symbol table
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        if (key == null) {
            throw new NullPointerException("argument to get() is null");
        }

        if (isEmpty()) {
            return null;
        }

        int i = rank(key);

        if (i < n && keys[i].compareTo(key) == 0) {
            return vals[i];
        }

        return null;
    }

    /**
     * Returns the number of keys in this symbol table strictly less than {@code key}.
     *
     * @param  key the key
     * @return the number of keys in the symbol table strictly less than {@code key}
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public int rank(Key key) {
        if (key == null) {
            throw new NullPointerException("argument to rank() is null");
        }

        int low = 0, high = n-1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = key.compareTo(keys[mid]);

            if (cmp < 0) {
                high = mid - 1;
            } else if (cmp > 0) {
                low = mid + 1;
            } else {
                return mid;
            }
        }

        return low;
    }

    /**
     * Insert the specified key-value pair into the table. If the key is already
     * in the table, overwrite its current value with the specified value.
     *
     * @param  key the key
     * @param  val the value
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null) {
            throw new NullPointerException("argument to put() is null");
        }

        if (val == null) {
            delete(key);
            return;
        }

        int i = rank(key);

        // key is already in the table
        if (i < n && keys[i].compareTo(key) == 0) {
            vals[i] = val;
            return;
        }

        // Insert new key-value pairs
        if (n == keys.length) {
            resize(2*keys.length);
        }

        for (int j = n; j > i; i--) {
            keys[j] = keys[j-1];
            vals[j] = vals[j-1];
        }

        keys[i] = key;
        vals[i] = val;
        n++;

        assert check();
    }

    /**
     * Removes the specified key and associated value from this symbol table
     * (if the key is in the symbol table).
     *
     * @param  key the key
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public void delete(Key key) {
        if (key == null) {
            throw new NullPointerException("argument to delete() is null");
        }

        if (isEmpty()) {
            return;
        }

        // Compute rank
        int i = rank(key);

        // Key not in table
        if (i == n || keys[i].compareTo(key) != 0) {
            return;
        }

        for (int j = i; j < n-1; j++) {
            keys[j] = keys[j+1];
            vals[j] = vals[j+1];
        }

        n--;
        keys[n] = null;  // To avoid loitering
        vals[n] = null;

        // Resize if 1/4 full
        if (n > 0 && n == keys.length / 4) {
            resize(keys.length / 2);
        }

        assert check();
    }
}
