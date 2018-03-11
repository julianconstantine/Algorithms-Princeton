/******************************************************************************************************************************************************************
Deque. A double-ended queue or deque (pronounced "deck") is a generalization of a stack and a queue that supports adding and removing items from either the front or the back of the data structure. Create a generic data type Deque that implements the following API:

public class Deque<Item> implements Iterable<Item> {
   public Deque()                           // construct an empty deque
   public boolean isEmpty()                 // is the deque empty?
   public int size()                        // return the number of items on the deque
   public void addFirst(Item item)          // add the item to the front
   public void addLast(Item item)           // add the item to the end
   public Item removeFirst()                // remove and return the item from the front
   public Item removeLast()                 // remove and return the item from the end
   public Iterator<Item> iterator()         // return an iterator over items in order from front to end
   public static void main(String[] args)   // unit testing
}

Corner cases. Throw a java.lang.NullPointerException if the client attempts to add a null item; throw a java.util.NoSuchElementException if the client attempts to remove an item from an empty deque; throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator; throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return.

Performance requirements.   Your deque implementation must support each deque operation in constant worst-case time. A deque containing n items must use at most 48n + 192 bytes of memory. and use space proportional to the number of items currently in the deque. Additionally, your iterator implementation must support each operation (including construction) in constant worst-case time.
******************************************************************************************************************************************************************/

import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    // Initialize pointer to front end of Stack to null
    private Node first = null;
    private Node last = null;
    private int size;

    // Private "inner class" Node for representing Items in Stack
    private class Node {
        Item item;  // Item stored in Node
        Node next;  // Reference to next Node
        Node prev;  // Reference to previous Node
    }

    // Class constructor
    public Deque() {
        size = 0;
    }

    public int size() {
        return size;
    }

    // Test if Stack is empty
    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("Item is null");
        }

        Node oldfirst = first;

        first = new Node();
        first.item = item;
        first.next = oldfirst;

        size++;

        if (size == 1) {
            last = first;
        } else {
            oldfirst.prev = first;
        }
    }

    // Add item at end of Deque (equivalent to enqueue())
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("Item is null");
        }

        Node oldlast = last;

        // Create a new terminal Node to store the new item
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldlast;

        size++;

        // If Deque is empty, set the "first" Node to the "last" Node
        if (size == 1) first = last;

        // Otherwise, set the "next" Node of the old terminal Node to be the new terminal Node
        else oldlast.next = last;
    }

    // Remove item from front of Deque (equivalent to dequeue())
    public Item removeFirst() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        Item item = first.item;
        first = first.next;

        size--;

        // If list is now empty, set the "last" Node to null
        if (this.isEmpty()) {
            last = null;
        } else {
            /***** DEQUE *****/
            first.prev = null;
            /***** DEQUE *****/
        }

        return item;
    }

    // Remove item from end of Deque
    public Item removeLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        Item item = last.item;
        last = last.prev;

        size--;

        if (this.isEmpty()) {
            //System.out.print(size + "-");
            last = null;
        }

        return item;
    }

    /********************
      IMPLEMENT ITERATION
    **********************/

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // Inner class ReverseArrayIterator for actual implementation
    private class DequeIterator implements Iterator<Item> {
        private Node head = first;
        private int counter = size;

        // Test if Iterator has any remaining items
        public boolean hasNext() {
            return counter > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException("Method remove() is not supported");
        }

        // Return next item in Iterator
        public Item next() {
            if (counter <= 0) {
                throw new NoSuchElementException("Iterator is empty");
            }

            Item item = head.item;

            counter--;

            if (counter > 0) {
                head = head.next;
            } else {
                head = null;
            }

            return item;
        }
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        // UNIT TEST #1
        // Print numbers 1 to n in order
        Deque<Integer> d = new Deque<Integer>();

        for (int i = 1; i <= n; i++) { d.addLast(i); }
        while (!d.isEmpty()) { System.out.print(d.removeFirst() + " "); }

        System.out.println();

        // UNIT TEST #2
        // Print numbers 1 to n in reverse order
        d = new Deque<Integer>();

        for (int i = 1; i <= n; i++) { d.addLast(i); }
        while (!d.isEmpty()) { System.out.print(d.removeLast() + " "); }

        System.out.println();

        // UNIT TEST #3
        // Print numbers 1 to n in order by adding to front and removing from rear
        d = new Deque<Integer>();

        for (int i = 1; i <= n; i++) { d.addFirst(i); }
        while (!d.isEmpty()) { System.out.print(d.removeLast() + " "); }

        System.out.println();

        // UNIT TEST #4
        // Print numbers 1 to n in order by adding to rear and removing from front
        d = new Deque<Integer>();

        for (int i = 1; i <= n; i++) { d.addLast(i); }
        while (!d.isEmpty()) { System.out.print(d.removeFirst() + " "); }

        System.out.println();

        // UNIT TEST #5
        // Print numbers 1 to in in reverse order by adding to front and removing from front
        d = new Deque<Integer>();

        for (int i = 1; i <= n; i++) { d.addFirst(i); }
        while (!d.isEmpty()) { System.out.print(d.removeFirst() + " "); }

        System.out.println();

    	// UNIT TEST #6
    	// Print numbers 1 to n by using iterator
    	d = new Deque<Integer>();

    	for (int i = 1; i <= n; i++) d.addLast(i);

    	// Shorthand syntax for iterator
    	for (int i: d) System.out.print(i + " ");

    	System.out.println();

    	// UNIT TEST #7
    	// Print out numbers 1 to n in reverse order by using iterator
    	d = new Deque<Integer>();

    	for (int i = 1; i <= n; i++) d.addFirst(i);

    	// Declare new Iterator
    	Iterator<Integer> itr = d.iterator();

    	// Print out numbers in reverse order
    	while (itr.hasNext()) {
    	    int i = itr.next();
    	    System.out.print(i + " ");
    	}

    	System.out.println();

        // UNIT TEST #9
        // Remove items from front of newly created empty Deque
        d = new Deque<Integer>();

        System.out.println(d.removeFirst());

        // UNIT TEST #10
        // Remove items from rear of newly created empty Deque\
        d = new Deque<Integer>();

        System.out.println(d.removeLast());

        // UNIT TEST #11
        // Remove item from front of Deque emptied after having numbers inserted/remove from it
        d = new Deque<Integer>();

        for (int i = 1; i <= n; i++) d.addFirst(i);

        while (!d.isEmpty()) d.removeLast();

        System.out.println(d.removeLast());
    }
}
