package bstmap;

import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    // The above line uses Java's bounded type parameter: extends Comparable<K>
    private BSTNode root;
    private int size;
    private final Set<K> setOfKeys;

    public BSTMap() {
        root = null;
        size = 0;
        setOfKeys = new HashSet<>();
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
        return containsKeyHelper(key, root);
    }

    private boolean containsKeyHelper(K key, BSTNode node) {
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
        if (!containsKey(key)) {
            return null;
        }
        return getHelper(key, root);
    }

    private V getHelper(K key, BSTNode node) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node.value;
        } else if (cmp < 0) {
            return getHelper(key, node.left);
        } else {
            return getHelper(key, node.right);
        }
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
        extractKey(root);
        return setOfKeys;
    }

    private void extractKey(BSTNode node) {
        if (node == null) {
            return;
        }
        setOfKeys.add(node.key);
        extractKey(node.left);
        extractKey(node.right);
    }

    @Override
    public V remove(K key) {
        // Inspired by https://math.oxford.emory.edu/site/cs171/hibbardDeletion/
        // and https://algs4.cs.princeton.edu/32bst/BST.java.html
        if (!containsKey(key)) {
            return null;
        }
        V returnValue = get(key);
        delete(key);
        size -= 1;
        return returnValue;
    }

    @Override
    public V remove(K key, V value) {
        if (!containsKey(key) || get(key) != value) {
            return null;
        }
        return remove(key);
    }

    private void delete(K key) {
        // Inspired by https://math.oxford.emory.edu/site/cs171/hibbardDeletion/
        // and https://algs4.cs.princeton.edu/32bst/BST.java.html
        root = deleteHelper(root, key);
    }

    /** Delete the successor of the node with Key = key in the BST. */
    private BSTNode deleteHelper(BSTNode n, K key) {
        // Inspired by https://math.oxford.emory.edu/site/cs171/hibbardDeletion/
        // and https://algs4.cs.princeton.edu/32bst/BST.java.html
        if (n == null) {
            return n;
        }
        int cmp = key.compareTo(n.key);
        if (cmp < 0) {
            n.left = deleteHelper(n.left, key);
        } else if (cmp > 0) {
            n.right = deleteHelper(n.right, key);
        } else {
            if (n.right == null) {
                return n.left;
            }
            if (n.left  == null) {
                return n.right;
            }
            BSTNode t = n;
            n = min(t.right);
            n.right = deleteMin(t.right);
            n.left = t.left;
        }
        return n;
    }

    private BSTNode min(BSTNode node) {
        BSTNode t = node;
        while (t.left != null) {
            t = t.left;
        }
        return t;
    }

    /** Delete the min-key node in the subtree of n*/
    private BSTNode deleteMin(BSTNode n) {
        // Inspired by https://math.oxford.emory.edu/site/cs171/hibbardDeletion/
        // and https://algs4.cs.princeton.edu/32bst/BST.java.html
        if (n.left == null) {
            return n.right; // current node is the min
        }
        // there are nodes smaller than the current node (and they are to the left)
        n.left = deleteMin(n.left);
        return n;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    private class BSTMapIterator implements Iterator<K> {
        private int wizPos;
        private final List<K> listOfKeys;
        BSTMapIterator() {
            extractKey(root);
            listOfKeys = new ArrayList<>(setOfKeys);
            wizPos = 0;
        }

        @Override
        public boolean hasNext() {
            return wizPos < setOfKeys.size();
        }

        @Override
        public K next() {
            K key = listOfKeys.get(wizPos);
            wizPos += 1;
            return key;
        }
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
