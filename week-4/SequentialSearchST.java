/******************************************************************************
 *  Compilation:  javac SequentialSearchST.java
 *  Execution:    java SequentialSearchST
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/31elementary/tinyST.txt
 *
 *  Symbol table implementation with sequential search in an
 *  unordered linked list of key-value pairs.
 *
 *  % more tinyST.txt
 *  S E A R C H E X A M P L E
 *
 *  % java SequentialSearchST < tiny.txt
 *  L 11
 *  P 10
 *  M 9
 *  X 7
 *  H 5
 *  C 4
 *  R 3
 *  A 8
 *  E 12
 *  S 0
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Queue;  // Import our Queue implementation

public class SequentialSearchST<Key, Value> {
    private int n;
    private Node first;

    // a helper linked list data type
    private class Node {
        private Key key;
        private Value val;
        private Node next;

        public Node(Key key, Value val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    /**
     * Initializes an empty symbol table
     */
    public SequentialSearchST() { }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return n;
    }

    /**
     * Returns true if this symbol table is empty
     *
     * @return  {@code true} if this symbol table is empty
     *          {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns true if this symbol table contains the specified key
     *
     * @param   key, the key to look up
     * @return  {@code true} if this symbol table contains {@code key}
     *          {@code false} otherwise
     * @throws  NullPointerException if {@code key} is {@code null}
     */
    public boolean contains(Key key) {
        if (key == null) {
            throw new NullPointerException("argument to contains() is null");
        }

        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key in this symbol table
     *
     * @param   key, the key to look up
     * @return  the value associated with key if key is in the symbol table,
                otherwise {@code null}
     * @throws  NullPointerException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        if (key == null) {
            throw new NullPointerException("argument to get() is null");
        }

        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                return x.val;
            }
        }

        return null;
    }

    /**
     * Returns the specified key-value pair into the symbol table, overwriting
     * the old value with the new value if the symbol table contains the specified
     * key. Deletes the specified key (and its associated value) from this symbol
     * table if the specified value is {@code: null}.
     *
     * @param   key, the key
     * @param   val, the value
     * @throws  NullPointerException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null) {
            throw new NullPointerException("first argument to put() is null");
        }

        // If key:value pair is key:null, delete key
        if (val == null) {
            delete(key);
            return;
        }

        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.val = val;
                return;
            }
        }

        first = new Node(key, val, first);
        n++;
    }

    /**
     * Removes the specified key and its associated value from the symbol table
     * (if the key is in this symbol table).
     *
     * @param   key, the key
     * @throws  NullPointerException if {@code key} is {@code null}
     */
    public void delete(Key key) {
        if (key == null) {
            throw new NullPointerException("argument to delete() is null");
        }

        first = delete(first, key);
    }

    // Delete key in linked list beginning at Node x
    // Warning: Function call stack too large if this table is large
    private Node delete(Node x, Key key) {
        if (x == null) {
            return null;
        }

        if (key.equals(x.key)) {
            n--;
            return x.next;
        }

        x.next = delete(x.next, key);
        return x;
    }

    /**
     * Returns all keys in the symbol table table as an {@code Iterable}.
     * To iterate over all of the keys in this symbol table named {@code st},
     * use the foreach notation: {@code for (Key key: st.keys())}.
     *
     * @return  all keys in the symbol table
     */
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();

        for (Node x = first; x != null; x = x.next) {
            queue.enqueue(x.key);
        }

        return queue;
    }

    /**
     * Unit tests the {@code SequentialSearchST} data type.
     *
     * @param   args, the command-line arguments
     */
     public static void main(String[] args) {
         SequentialSearchST<String, Integer> st = new SequentialSearchST<String, Integer>();

         for (int i = 0; !StdIn.isEmpty(); i++) {
             String key = StdIn.readString();
             st.put(key, i);
         }

         for (String s: st.keys()) {
             System.out.println(s + " " + st.get(s));
         }
     }
}
