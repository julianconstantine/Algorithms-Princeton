/********************************************************************************************************************************************************************
Subset client. Write a client program Subset.java that takes a command-line integer k; reads in a sequence of N strings from standard input using StdIn.readString(); and prints out exactly k of them, uniformly at random. Each item from the sequence can be printed out at most once. You may assume that 0 ≤ k ≤ n, where N is the number of string on standard input.

% echo A B C D E F G H I | java Subset 3       % echo AA BB BB BB BB BB CC CC | java Subset 8
C                                              BB
G                                              AA
A                                              BB
                                               CC
% echo A B C D E F G H I | java Subset 3       BB
E                                              BB
F                                              CC
G                                              BB
The running time of Subset must be linear in the size of the input. You may use only a constant amount of memory plus either one Deque or RandomizedQueue object of maximum size at most n, where n is the number of strings on standard input. (For an extra challenge, use only one Deque or RandomizedQueue object of maximum size at most k.) It should have the following API.

public class Subset {
   public static void main(String[] args)
}

********************************************************************************************************************************************************************/

import edu.princeton.cs.algs4.StdIn;

public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> rQueue = new RandomizedQueue<String>();

        // Read in N strings from standard input and enqueue them in a RandomizedQueue
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();

            rQueue.enqueue(s);
        }

        // Print out k of the N inputs, chosen randomly
        for (int i = 0; i < k; i++) System.out.println(rQueue.dequeue());
    }
}
