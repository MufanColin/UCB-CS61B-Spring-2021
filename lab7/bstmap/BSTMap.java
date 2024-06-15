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
        public BSTNode left, right, parent;
        public BSTNode() {
            key = null;
            value = null;
            left = null;
            right = null;
            parent = null;
        }

        public BSTNode(K k, V v) {
            key = k;
            value = v;
            left = null;
            right = null;
            parent = null;
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
            BSTNode lchild = putHelper(key, value, node.left);
            node.left = lchild;
            lchild.parent = node;
        } else {
            BSTNode rchild = putHelper(key, value, node.right);
            node.right = rchild;
            rchild.parent = node;
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
        if (!containsKey(key)) {
            return null;
        }
        V value = get(key);
        BSTNode nodeDelete = find(key);
        if (nodeDelete.left != null && nodeDelete.right != null) {
            BSTNode nodeToReplace = largestNodeInLeftSubTree(nodeDelete.left);
            if (nodeDelete.parent != null) {
                // nodeDelete is not the root of the tree
                if (nodeToReplace.parent == nodeDelete) {
                    nodeToReplace.parent = nodeDelete.parent;
                    // do not have to change nodeToReplace.left
                    nodeToReplace.right = nodeDelete.right;
                    nodeDelete.right.parent = nodeToReplace;
                } else {
                    nodeToReplace.parent = nodeDelete.parent;
                    nodeToReplace.left = nodeDelete.left;
                    nodeToReplace.right = nodeDelete.right;
                    nodeDelete.left.parent = nodeToReplace;
                    nodeDelete.right.parent = nodeToReplace;
                }
                if (nodeDelete.parent.left == nodeDelete) {
                    nodeDelete.parent.left = nodeToReplace;
                } else {
                    nodeDelete.parent.right = nodeToReplace;
                }
            } else {
                // nodeDelete is the root of the tree
                root = nodeToReplace;
                root.right = nodeDelete.right;
                nodeDelete.right.parent = root;
            }
        } else if (nodeDelete.left != null) {
            nodeDelete.left.parent = nodeDelete.parent;
            if (nodeDelete.parent != null) {
                if (nodeDelete.parent.left == nodeDelete) {
                    nodeDelete.parent.left = nodeDelete.left;
                } else {
                    nodeDelete.parent.right = nodeDelete.left;
                }
            } else {
                nodeDelete = nodeDelete.left;
                root = nodeDelete;
            }
        } else if (nodeDelete.right != null) {
            nodeDelete.right.parent = nodeDelete.parent;
            if (nodeDelete.parent != null) {
                if (nodeDelete.parent.left == nodeDelete) {
                    nodeDelete.parent.left = nodeDelete.right;
                } else {
                    nodeDelete.parent.right = nodeDelete.right;
                }
            } else {
                nodeDelete = nodeDelete.right;
                root = nodeDelete;
            }
        } else {
            if (nodeDelete.parent != null) {
                if (nodeDelete.parent.left == nodeDelete) {
                    nodeDelete.parent.left = null;
                } else {
                    nodeDelete.parent.right = null;
                }
            } else {
                root = null;
            }
        }
        size -= 1;
        return value;
    }

    @Override
    public V remove(K key, V value) {
        if (!containsKey(key)) {
            return null;
        }
        V currentValue = get(key);
        if (currentValue.equals(value)) {
            return remove(key);
        } else {
            return null;
        }
    }

    private BSTNode find(K key) {
        return findHelper(key, root);
    }

    private BSTNode findHelper(K key, BSTNode node) {
        if (node == null || key.equals(node.key)) {
            return node;
        }
        BSTNode leftResult = findHelper(key, node.left);
        BSTNode rightResult = findHelper(key, node.right);
        if (leftResult != null) {
            return leftResult;
        }
        return rightResult;
    }

    private BSTNode largestNodeInLeftSubTree(BSTNode node) {
        BSTNode n = node;
        while (n.right != null) {
            n = n.right;
        }
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
