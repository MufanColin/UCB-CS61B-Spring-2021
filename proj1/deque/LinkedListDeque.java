package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private final Node sentinel;
    private Node recursiveSentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        recursiveSentinel = sentinel;
        size = 0;
    }

    private class Node {
        private final T item;
        private Node prev;
        private Node next;
        Node(T i, Node p, Node n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    @Override
    public void addFirst(T item) {
        Node t = sentinel.next; // the current front node
        Node n = new Node(item, sentinel, sentinel.next); // the new front node
        sentinel.next = n;
        t.prev = n;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        Node t = sentinel.prev; // the current tail node
        Node n = new Node(item, t, sentinel);
        sentinel.prev = n;
        t.next = n;
        size += 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T value = sentinel.next.item;
        Node t = sentinel.next;
        t.next.prev = sentinel;
        sentinel.next = t.next;
        t = null;
        size -= 1;
        return value;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T value = sentinel.prev.item;
        Node t = sentinel.prev;
        t.prev.next = sentinel;
        sentinel.prev = t.prev;
        t = null;
        size -= 1;
        return value;
    }

    @Override
    public T get(int index) {
        if (isEmpty() || index > size() - 1) {
            return null;
        }
        Node p = sentinel.next;
        int cnt = 0;
        while (cnt < index) {
            cnt += 1;
            p = p.next;
        }
        return p.item;
    }

    public T getRecursive(int index) {
        // We can also use a helper function to implement getRecursive()
        if (isEmpty() || index > size() - 1) {
            return null;
        }
        if (index < 0) {
            T value = recursiveSentinel.item;
            recursiveSentinel = sentinel; // multiple calls
            return value;
        }
        recursiveSentinel = recursiveSentinel.next;
        return getRecursive(index - 1);
    }

    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node wizNode;
        public LinkedListDequeIterator() {
            wizNode = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return wizNode != sentinel;
        }

        @Override
        public T next() {
            T returnValue = wizNode.item;
            wizNode = wizNode.next;
            return returnValue;
        }
    }

    /** Returns whether or not the parameter o is equal to the Deque.
     * o is considered equal if it is a Deque and if it contains the same contents
     * (as governed by the generic T’s equals method) in the same order.*/
    public boolean equals(Object o) {
        if (this == o) {
            return true; // for efficiency
        }
        if (o == null) {
            return false;
        }
        if (o instanceof Deque other) {
            if (this.size() != other.size()) {
                return false;
            }
            for (int i = 0; i < this.size(); i++) {
                if (!this.get(i).equals(other.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
