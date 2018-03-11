// Linked-list implementation of Queue data structure

public class LinkedListQueueOfStrings {
    // Maintain two pointers to first AND last elements of list
    private Node first, last;
    
    // Inner class Node for storing items in linked list
    private class Node {
	String item;  // Item in this Node
	Node next;  // Reference to next Node
    }

    // Test if list is empty
    public boolean isEmpty() {
	return first == null;
    }

    // Enqueue item at end of Queue
    public void enqueue(String item) {
	Node oldLast = last;

	// Create a new terminal Node to store the new item
	last = new Node();
	last.item = item;
	last.next = null;
	
	// If Queue is empty, set the "first" Node to the "last" Node
	if (this.isEmpty()) first = last;

	// Otherwise, set the "next" Node of the old terminal Node to be the new terminal Node
	else oldLast.next = last;
    }

    // Dequeue item from front of Queue
    public String dequeue() {
	String item = first.item;
	first = first.next;
	
	// If list is now empty, set the "last" Node to null
	if (this.isEmpty()) last = null;
       
	return item;
    }
}