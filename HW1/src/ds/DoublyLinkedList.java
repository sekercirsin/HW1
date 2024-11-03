package ds;

public class DoublyLinkedList<T> {
    private class Node {
        T data;
        Node next, prev;

        Node(T data) {
            this.data = data;
        }
    }

    private Node head, tail;

    public void addLast(T data) {
        Node newNode = new Node(data);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    public T removeLast() {
        if (tail == null)
            return null;
        T data = tail.data;
        if (head == tail) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        return data;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public T removeAt(int index) {
        if (index < 0 || head == null)
            return null;

        Node current = head;
        for (int i = 0; i < index && current != null; i++) {
            current = current.next;
        }
        if (current == null)
            return null;

        if (current.prev != null)
            current.prev.next = current.next;
        else
            head = current.next;

        if (current.next != null)
            current.next.prev = current.prev;
        else
            tail = current.prev;

        return current.data;
    }

}
