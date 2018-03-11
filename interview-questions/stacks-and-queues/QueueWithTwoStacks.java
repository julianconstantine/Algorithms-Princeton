// Queue with two stacks.
// Implement a queue with two stacks so that each queue operations takes a constant amortized number of stack operations.

public class QueueWithTwoStacks<Item> {
    private ResizingArrayStack<Item> forward;
    private ResizingArrayStack<Item> reverse;

    // Class constructor
    public QueueWithTwoStacks() {}

    public boolean isEmpty() {
        return forward.isEmpty() && reverse.isEmpty();
    }

    public void enqueue(Item item) {
        forward.push(item);
    }

    public Item dequeue() {
        if (reverse.isEmpty()) {
            // If reverse queue is empty, pop everything out of the forward queue and put it into the reverse queue
            while (!forward.isEmpty()) {
                Item item = forward.pop();
                reverse.push(item);
            }
        }

        return reverse.pop();
    }

    // Test client
    public static void main(String[] args) {
        QueueWithTwoStacks<String> q2s = new QueueWithTwoStacks<String>();

        q2s.enqueue("a");
        q2s.enqueue("b");
        q2s.enqueue("c");
        q2s.dequeue();
        q2s.enqueue("d");
        q2s.dequeue();
        q2s.dequeue();
        q2s.enqueue("e");
        q2s.dequeue();
        q2s.dequeue();
    }
}
