// Generic, Iterable implementation of linked-list stack

import java.util.Iterator;

public class LinkedListStack<Item> implements Iterable<Item> {
    // Initialize pointer to front end of Stack to null
    private Node first = null;

    // Private "inner class" Node for representing Items in Stack
    private class Node {
        Item item;  // Item stored in Node
        Node next;  // Reference to next Node
    }

    // Test if Stack is empty
    public boolean isEmpty() {
        return first == null;
    }

    // Push item onto Stack
    public void push(Item item) {
        Node oldfirst = first;

        first = new Node();
        first.item = item;
        first.next = oldfirst;
    }

    // Pop item from stack
    public Item pop() {
        Item item = first.item;

        first = first.next;

        return item;
    }

    /* ********************
      IMPLEMENT AS ITERABLE
      ******************** */

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // Inner class ListIterator for the actual implementation
    private class ListIterator implements Iterator<Item> {
        // Initialize current Node reference to first item in list
        private Node current = first;

        // Check if Iterator has any remaining items
        public boolean hasNext() {
            return current != null;
        }

        // Return next item in Iterator
        public Item next() {
            Item item = current.item;

            current = current.next;

            return item;
        }
    }
}
