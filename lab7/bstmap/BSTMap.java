package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    // The above line uses Java's bounded type parameter: extends Comparable<K>
    private BSTNode root;
    private int size;

    public BSTMap() {
        root = null;
        size = 0;
    }

    private class BSTNode {
        public K key;
        public V value;
        public BSTNode left, right;
        public BSTNode() {
            key = null;
            value = null;
            left = null;
            right = null;
        }

        public BSTNode(K k, V v) {
            key = k;
            value = v;
            left = null;
            right = null;
        }
    }

    @Override
    public void clear() {
        root = clearHelper(root);
        size = 0;
    }

    private BSTNode clearHelper(BSTNode node) {
        if (node == null) {
            return node;
        }
        node.left = clearHelper(node.left);
        node.right = clearHelper(node.right);
        node = null;
        return node;
    }

    @Override
    public boolean containsKey(K key) {
        return key != null && containsKeyHelper(key, root);
    }

    private boolean containsKeyHelper(K key, BSTNode node) {
        // We can assume that key won't be null here.
        if (node == null) {
            return false;
        }
        // do not use == for comparison, use .equals instead!!!
        return key.equals(node.key)
                || containsKeyHelper(key, node.left)
                || containsKeyHelper(key, node.right);
    }

    @Override
    public V get(K key) {
        if (key == null) {
            return null;
        }
        return getHelper(key, root);
    }

    private V getHelper(K key, BSTNode node) {
        if (node == null) {
            return null;
        } else if (key.equals(node.key)) {
            return node.value;
        } else if (key.compareTo(node.key) < 0) {
            V value = getHelper(key, node.left);
            if (value != null) {
                return value;
            }
        } else {
            V value = getHelper(key, node.right);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (!containsKey(key)) {
            root = putHelper(key, value, root);
            size += 1;
        }
    }

    private BSTNode putHelper(K key, V value, BSTNode node) {
        if (node == null) {
            node = new BSTNode(key, value);
        } else if (key.compareTo(node.key) < 0) {
            node.left = putHelper(key, value, node.left);
        } else {
            node.right = putHelper(key, value, node.right);
        }
        return node;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    /** Prints out your BSTMap in order of increasing Key. */
    public void printInOrder() {
        printInOrderHelper(root);
    }

    private void printInOrderHelper(BSTNode node) {
        if (node == null) {
            return;
        }
        printInOrderHelper(node.left);
        System.out.println("Key: " + node.key + " Value: " + node.value);
        printInOrderHelper(node.right);
    }
}
