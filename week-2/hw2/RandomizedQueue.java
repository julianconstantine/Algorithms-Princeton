/******************************************************************************************************************************************************************
Randomized queue. A randomized queue is similar to a stack or queue, except that the item removed is chosen uniformly at random from items in the data structure. Create a generic data type RandomizedQueue that implements the following API:

public class RandomizedQueue<Item> implements Iterable<Item> {
   public RandomizedQueue()                 // construct an empty randomized queue
   public boolean isEmpty()                 // is the queue empty?
   public int size()                        // return the number of items on the queue
   public void enqueue(Item item)           // add the item
   public Item dequeue()                    // remove and return a random item
   public Item sample()                     // return (but do not remove) a random item
   public Iterator<Item> iterator()         // return an independent iterator over items in random order
   public static void main(String[] args)   // unit testing
}

Corner cases. The order of two or more iterators to the same randomized queue must be mutually independent; each iterator must maintain its own random order. Throw a java.lang.NullPointerException if the client attempts to add a null item; throw a java.util.NoSuchElementException if the client attempts to sample or dequeue an item from an empty randomized queue; throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator; throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return.

Performance requirements. Your randomized queue implementation must support each randomized queue operation (besides creating an iterator) in constant amortized time. That is, any sequence of m randomized queue operations (starting from an empty queue) should take at most cm steps in the worst case, for some constant c. A randomized queue containing n items must use at most 48n + 192 bytes of memory. Additionally, your iterator implementation must support operations next() and hasNext() in constant worst-case time; and construction in linear time; you may (and will need to) use a linear amount of extra memory per iterator.
******************************************************************************************************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;

    private int N = 0;


    public RandomizedQueue() {
        // Initialize array of size 1
        // RECALL: Cannot declare generic arrays in Java, so need to cast to Item
        q = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    private void resize(int capacity) {
        // Declare new array
        // RECALL: Cannot declare generic arrays in Java, so need to cast to Item
        Item[] copy = (Item[]) new Object[capacity];

        // Copy over all N items from q to copy
        for (int i = 0; i < N; i++) {
            copy[i] = q[i];
        }

        // Overwrite q with the new (resized) array copy
        q = copy;
    }

    // Add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("Item is null");
        }

        if (q.length == N) {
            resize(2 * q.length);
        }

        // Add new item
        q[N++] = item;
    }

    // Remove and return a random item
    public Item dequeue() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("RandomizedQueue is empty");
        }

        // Generate a random number between 0 and N - 1
        int m = StdRandom.uniform(N);

        Item item = q[m];

        // Decrement N, then movei tem at end of queue to mth position
        q[m] = q[--N];

        // Set item in last position to null
        q[N] = null;

        // Resize array by halving when array is one-quarter full
        if (N > 0 && N == q.length/4) {
            resize(q.length/2);
        }

        return item;
    }

    // Return (but do not remove) a random item
    public Item sample() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("RandomizedQueue is empty");
        }

        // Generate a random number between 0 and N - 1
        int m = StdRandom.uniform(N);

        Item item = q[m];

        return item;
    }

    /*************************
        IMPLEMENT ITERATION
    *************************/

    // Return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int counter = 0;

        public boolean hasNext() {
            return counter < N;
        }

        public void remove() {
            throw new UnsupportedOperationException("Method remove() is not supported");
        }

        public Item next() {
            if (counter >= N) {
                throw new NoSuchElementException("Iterator is empty");
            }

            return q[counter++];
        }
    }

    // Unit tests
    public static void main(String[] args) {
        // Size of tests
        int n = Integer.parseInt(args[0]);

        // UNIT TEST #1
        // Insert nubmers 1 through n and print them out in random order
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

        for (int i = 1; i <= n; i++) rq.enqueue(i);

        while (!rq.isEmpty()) System.out.print(rq.dequeue() + " ");

        System.out.println();

        // UNIT TEST #2
        // Insert numbers 1 through n and print them out in order by using an Iterator
        rq = new RandomizedQueue<Integer>();

        for (int i = 1; i <= n; i++) rq.enqueue(i);

        Iterator<Integer> itr = rq.iterator();

        while (itr.hasNext()) System.out.print(itr.next() + " ");

        System.out.println();
    }

}
