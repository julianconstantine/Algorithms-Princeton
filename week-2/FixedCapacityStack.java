// Generic, Iterable  implementation of fixed-capacity Stack
// Java does NOT allow for generic array creation, so we have to use casting

public class FixedCapacityStack<Item> {
    // Declare generic array (CAN do this)
    private Item[] s;

    // Initialize number of items in stack to zero
    private int N = 0;

    // Class constructor
    public FixedCapacityStack(int capacity) {
	// Initialize generic array s by casting (ugly!)
	s = (Item[]) new Object[capacity];
    }
    
    // Test if stack is empty
    public boolean isEmpty() {
	return N == 0;
    }

    // Push item onto stack
    public void push(Item item) {
	s[N++] = item;
    }

    // Pop item from stack
    public Item pop() {
	return s[--N];
    }

    /********************
      IMPLEMENT ITERATION
    **********************/

    public Iterator<Item> iterator() {
	return new ReverseArrayIterator();
    }

    // Inner class ReverseArrayIterator for actual implementation
    private class ReverseArrayIterator implements Iterator<Item> {
	private int i = N;
	
	// Test if Iterator has any remaining items
	private boolean hasNext() {
	    return i > 0;
	}
	
	// Return next item in Iterator
	public Item next() {
	    return s[--i];
	}
    }
}