// Resizing array  implementation of Stack (of Strings)

public class FixedCapacityStackOfStrings {
    // Declare array for stack (this is an instance variable?)
    private String[] s;

    // Initialize number of objects in stack to zero
    private int N = 0;

    // Class constructor (because we need to create the array)
    public ResizingArrayStackOfStrings() {
        s = new String[1];
    }

    // Test if stack is empty
    public boolean isEmpty() {
        return N == 0;
    }

    // Push item onto stack
    public void push(String item) {
        // If array fills up, resize by repeated doubling
        if (N == s.length) resize(2 * s.length);

        // Index into array, store item, then increment N
        s[N++] = item;
    }

    // Create new array of size capacity
    public void resize(int capacity) {
        String[] copy = new String[capacity];

        // Copy over old array into larger array
        for (int i = 0; i < N; i++) {
            copy[i] = s[i];
        }

        // Overwrite old array with new array
        s = copy;
    }

    // Pop item from stack
    public String pop() {
        // Decrement N, then index into array to retrieve item
        String item =  s[--N];

        // Remove reference to unneeded object (avoids "loitering")
        s[N] = null;

        // Resize array by halving when array is one-quarter full
        if ( N > 0 && N == s.length/4) resize(s.length/2);

        return item;
    }
}
