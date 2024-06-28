package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Colin Mufan
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int initialSize;
    private int numElements;
    private final double loadFactor;
    private final HashSet<K> keySet;

    /** Constructors */
    public MyHashMap() {
        this.initialSize = 16; // default value
        this.loadFactor = 0.75; // default value
        this.numElements = 0;
        this.keySet = new HashSet<>();
        buckets = new Collection[initialSize];
        for (int i = 0; i < initialSize; i++) {
            buckets[i] = createBucket();
        }
    }

    public MyHashMap(int initialSize) {
        this.initialSize = initialSize; // default value
        this.loadFactor = 0.75; // default value
        this.numElements = 0;
        this.keySet = new HashSet<>();
        buckets = new Collection[initialSize];
        for (int i = 0; i < initialSize; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.initialSize = initialSize;
        this.loadFactor = maxLoad;
        this.numElements = 0;
        this.keySet = new HashSet<>();
        buckets = new Collection[initialSize];
        for (int i = 0; i < initialSize; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] newBuckets = new Collection[tableSize];
        for (int i = 0; i < tableSize; i++) {
            newBuckets[i] = createBucket();
        }
        int initialSizeCopy = initialSize;
        initialSize = tableSize;
        for (int i = 0; i < initialSizeCopy; i++) {
            for (Node n: buckets[i]) {
                int newBucketIndex = getBucketIndex(n.key);
                newBuckets[newBucketIndex].add(n);
            }
        }
        return newBuckets;
    }

    @Override
    public void clear() {
        for (int i = 0; i < initialSize; i++) {
            buckets[i].clear();
        }
        numElements = 0;
    }

    private int getBucketIndex(K key) {
        int hashcode = key.hashCode();
        return Math.floorMod(hashcode, this.initialSize);
    }

    @Override
    public boolean containsKey(K key) {
        int bucketIndex = getBucketIndex(key);
        for (Node n: buckets[bucketIndex]) {
            if (key.equals(n.key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        V value = null;
        int bucketIndex = getBucketIndex(key);
        for (Node n: buckets[bucketIndex]) {
            if (key.equals(n.key)) {
                value = n.value;
                break;
            }
        }
        return value;
    }

    @Override
    public int size() {
        return numElements;
    }

    private boolean needToResize(int numElements, int size){
        return numElements * 1.0 / size > this.loadFactor;
    }

    @Override
    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        if (!containsKey(key)) {
            keySet.add(key);
            Node n = createNode(key, value);
            buckets[bucketIndex].add(n);
            numElements += 1;
        } else {
            for (Node n: buckets[bucketIndex]) {
                if (key.equals(n.key)) {
                    n.value = value;
                    break;
                }
            }
        }
        if (needToResize(numElements, size())) {
            buckets = createTable(size() * 2);
        }
    }

    @Override
    public Set<K> keySet() {
        return keySet;
    }

    @Override
    public V remove(K key) {
        V value = null;
        if (containsKey(key)) {
            int bucketIndex = getBucketIndex(key);
            for (Node n : buckets[bucketIndex]) {
                if (key.equals(n.key)) {
                    value = n.value;
                    buckets[bucketIndex].remove(n);
                    break;
                }
            }
            numElements -= 1;
            keySet.remove(key);
        }
        return value;
    }

    @Override
    public V remove(K key, V value) {
        if (get(key) == value) {
            return remove(key);
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet.iterator();
    }
}
