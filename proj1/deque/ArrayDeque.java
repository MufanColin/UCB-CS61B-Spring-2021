package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int size;
    private int headIndex; // where to addFirst
    private int tailIndex; // where to addLast

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        headIndex = 0;
        tailIndex = 1;
    }

    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        for (int i = 0; i < size(); i++) {
            newItems[i] = items[(headIndex + 1 + i) % items.length];
        }
        headIndex = capacity - 1;
        tailIndex = size();
        items = newItems;
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[(headIndex + items.length) % items.length] = item;
        headIndex = (headIndex - 1 + items.length) % items.length;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[tailIndex % items.length] = item;
        tailIndex = (tailIndex + 1) % items.length;
        size += 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size(); i++) {
            System.out.print(items[(headIndex + 1 + i) % items.length] + " ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        if (size() >= 16 && (double) (size() - 1) / items.length < 0.25) {
            resize(items.length / 2);
        }
        T value = items[(headIndex + 1) % items.length];
        headIndex = (headIndex + 1) % items.length;
        size -= 1;
        return value;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (size() >= 16 && (double) (size() - 1) / items.length < 0.25) {
            resize(items.length / 2);
        }
        T value = items[(tailIndex - 1 + items.length) % items.length];
        tailIndex = (tailIndex - 1 + items.length) % items.length;
        size -= 1;
        return value;
    }

    @Override
    public T get(int index) {
        if (isEmpty() || index > size() - 1) {
            return null;
        }
        return items[(headIndex + 1 + index) % items.length];
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int wizPos;

        public ArrayDequeIterator() {
            wizPos = 0;
        }

        @Override
        public boolean hasNext() {
            return wizPos < size();
        }

        @Override
        public T next() {
            T returnItem = items[(headIndex + 1 + wizPos) % items.length];
            wizPos += 1;
            return returnItem;
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
        // if (o instanceof Deque other)
        if (o instanceof Deque) {
            Deque other = (Deque) o; // if the autograder does not support o instanceof Deque other, we do this instead.
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
