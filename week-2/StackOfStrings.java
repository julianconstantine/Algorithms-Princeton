// Template for implementing the Stack (of Strings) data type

public class StackOfStrings {
    // StackOfStrings Test Client
    public static void main(String[] args) {
	StackOfStrings stack = new StackOfStrings();
	
	while (!StdIn.isEmpty()) {
	    // Reads strings from standard input and
	    String s = StdIn.readString();
	    
	    // Is string equals "-", pop string and print;
	    if (s.equals("-")) StdOut.print(stack.pop());
	    
	    // Otherwise, push string onto stack
	    else stack.push(s);   
	}
    }
}