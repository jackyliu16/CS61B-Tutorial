package lab9;

import java.util.*;
import java.util.function.Consumer;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        // if empty
        if (p == null) {
            return null;
        }
        // if equal
        int flag = p.key.compareTo(key);
        if (flag < 0) {
            // if this < param
            return getHelper(key, p.right);
        } else if (flag == 0) {
            // if this == param
            return p.value;
        } else {
            // if this > param
            return getHelper(key, p.left);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, this.root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            return new Node(key, value);
        }

        int flag = p.key.compareTo(key);
        if (flag < 0) {
            // this < param
            p.right = putHelper(key, value, p.right);
            return p;
        } else if (flag == 0) {
            // replace
            assert key == p.key;
            p.value = value;
            return p;
        } else {
            // this > param
            p.left = putHelper(key, value, p.left);
            return p;
        }
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        this.size++;
        this.root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return this.size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        TreeSet<K> set = new TreeSet<>();
        for (K item: this) {
            set.add(item);
        }
        return set;
    }



    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIter();
    }

    private class BSTMapIter implements Iterator<K> {
        private int pos;
        private int cnt;
        private ArrayList<Node> node_list;

        BSTMapIter() {
            pos = 0;    // the position in node_list
            cnt = 0;
            node_list = getHelper(root);
        }

        @Override
        public boolean hasNext() {
            return pos < size;
        }

        @Override
        public K next() {
            return node_list.get(pos++).key;
        }

        public ArrayList<Node> getHelper(Node p) {
            ArrayList<Node> res = new ArrayList<>();
            Stack<Node> stack = new Stack<>();

            stack.add(root);
            while (!stack.isEmpty()) {
                Node t = stack.pop();
                res.add(t);

                if (t.right != null) {
                    stack.add(t.right);
                }
                if (t.left != null) {
                    stack.add(t.left);
                }
            }
            return res;
        }

        @Override
        public void forEachRemaining(Consumer<? super K> action) {
            Iterator.super.forEachRemaining(action);
        }
    }
}
