package ds;

public class Stack<T> {
    private DoublyLinkedList<T> list = new DoublyLinkedList<>();

    public void push(T data) {
        list.addLast(data);
    }

    public T pop() {
        return list.removeLast();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}
