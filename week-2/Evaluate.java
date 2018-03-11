/********************************************
  IMPLEMENT'S DIJKSTRA'S TWO-STACK ALGORITHM
*********************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;

public class Evaluate {
    public static void main(String[] args) {
	// Initialize operator and value stacks
	Stack<String> ops = new Stack<String>();
	Stack<Double> vals = new Stack<Double>();

	while (!StdIn.isEmpty()) {
	    String s = StdIn.readString();

	    if (s.equals("(")) ;
	    else if (s.equals("+")) ops.push(s);
	    else if (s.equals("*")) ops.push(s);
    	    else if (s.equals(")")) {
		// Pop the operator stack
		String op = ops.pop();
		
		// Pop the value stack twice and perform the most recently opped operation on those two values
		if (op.equals("+")) {
		    vals.push(vals.pop() + vals.pop());
		} else if (op.equals("*")) {
		    vals.push(vals.pop() * vals.pop());
		}
	    }
     	    else {
		// Otherwise, push the value being read onto the stack
		vals.push(Double.parseDouble(s));
	    }
	}
	// Print the last item on the value stack
	StdOut.println(vals.pop());
    }
}