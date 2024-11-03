package ds;

public class Queue<T> {
    private DoublyLinkedList<T> list = new DoublyLinkedList<>();

    public void enqueue(T data) {
        list.addLast(data);
    }

    public T dequeue() {
        return list.removeLast();  // Adjust if necessary to remove from the head
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}
