// Linked-List implementation of StackOfStrings
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class LinkedListStackOfStrings {
    // Initialize "empty" first Node
    private Node first = null;

    // Inner class "Node" for representing the items in the linked list
    private class Node {
	String item;  // item contained in this Node
	Node next;  // reference to next Node
    }

    public boolean isEmpty() {
	return first == null;
    }

    // Push new item onto stack by resetting the "first" Node
    public void push(String item) {
	Node oldFirst = first;

	first = new Node();
	first.item = item;
	first.next = oldFirst;
    }

    // Pop item from the stack by resetting the "first" Node to the following Node
    public String pop() {
	String item = first.item;
	first = first.next;

	return item;
    }
}