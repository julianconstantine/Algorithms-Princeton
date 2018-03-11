/******************************************************************************
 *  Compilation:  javac ResizingArrayStack.java
 *  Execution:    java ResizingArrayStack < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/13stacks/tobe.txt
 *
 *  Stack implementation with a resizing array.
 *
 *  % more tobe.txt
 *  to be or not to - be - - that - - - is
 *
 *  % java ResizingArrayStack < tobe.txt
 *  to be not that or be (2 left on stack)
 *
 ******************************************************************************/

 import java.util.Iterator;
 import java.util.NoSuchElementException;
 import java.util.Arrays;

public class ResizingArrayStack<Item> implements Iterable<Item> {
    private Item[] a;  // Array of items
    private int n;  // Number of elements on stack

    /**
     * Initialize an empty stack
     */
     public ResizingArrayStack() {
         a = (Item[]) new Object[2];
         n = 0;
     }

     /**
      * Is this stack empty?
      */
     public boolean isEmpty() {
         return n == 0;
     }

     /**
      * Returns number of items in the stack
      */
      public int size() {
          return n;
      }

      // Resize the underlying array holding the elements
      private void resize(int capacity) {
          assert capacity >= n;

          /* Textbook implementation
          Item[] temp = (Item[]) new Object[capacity];
          for (int i = 0; i < n; i++) {
              temp[i] = a[i];
          }
          a = temp;
          */

          // Alternative implementation
          a = java.util.Arrays.copyOf(a, capacity);
      }

      /**
       * Adds the item to the stack
       */
      public void push(Item item) {
          if (n == a.length) resize(2*a.length);
          a[n++] = item;
      }

      /**
       * Removes are returns the item most recently added to the stack
       */
       public Item pop() {
           if (isEmpty()) {
               throw new NoSuchElementException("Stack underflow");
           }

           Item item = a[n-1];
           a[n-1] = null;
           n--;

           // Shrink size of the array if necessary
           if (n > 0 && n == a.length/4) {
               resize(a.length/2);
           }

           return item;
       }

       /**
        * Returns (but does not remove) the item most recently added to the stack
        */
       public Item peek() {
           if (isEmpty()) {
               throw new NoSuchElementException("Stack underflow");
           }

           return a[n-1];
       }

       /**
        * Returns an iterator to this stack that iterates through the item in LIFO order
        */
       public Iterator<Item> iterator() {
           return new ReverseArrayIterator();
       }

       private class ReverseArrayIterator implements Iterator<Item> {
           private int i;

           public ReverseArrayIterator() {
               i = n-1;
           }

           public boolean hasNext() {
               return i >= 0;
           }

           public void remove() {
               throw new UnsupportedOperationException();
           }

           public Item next() {
               if (!hasNext()) {
                   throw new NoSuchElementException();
               }

               return a[i--];
           }
       }
}
