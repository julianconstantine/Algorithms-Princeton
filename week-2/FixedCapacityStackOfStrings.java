// Fixed-capacity array implementation of Stack (of Strings)

public class FixedCapacityStackOfStrings {
    // Declare array for stack (this is an instance variable?)
    private String[] s;

    // Initialize number of objects in stack to zero
    private int N = 0;

    // Class constructor (because we need to create the array)
    public FixedCapacityStackOfStrings(int capacity) {
	s = new String[capacity];
    }

    // Test if stack is empty
    public boolean isEmpty() {
	return N == 0;
    }
    
    // Push item onto stack
    public void push(String item) {
	// Index into array, store item, then increment N
	s[N++] = item;
    }

    // Pop item from stack
    public String pop() {
	// Decrement N, then index into array to retrieve item
	String item =  s[--N];

	// Remove reference to unneeded object (avoids "loitering")
	s[N] = null;

	return item;
    }
}