package ru.job4j.map;


import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * This is simplified version of HashMap
 * based on array and linked list.
 *
 * @param <K> key
 * @param <V> value
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @since 0.1
 */
public class SimpleHashMap<K, V> implements SimpleMap<K, V> {

    /**
     * General hash table
     */
    private Node<K, V>[] table;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    /**
     * Default load. It defines size 3\4 from total capacity hash table
     */
    private static final float DEFAULT_LOAD_FACTORY = 0.75f;
    /**
     * This is max capacity of table 2^30 or 1073741824 cells in hash table
     */
    private static final int MAX_CAPACITY = 1 << 30;
    /**
     * Current threshold
     */
    private int threshold;
    /**
     * Current capacity of table;
     */
    private int capacity;
    /**
     * Current size (number elements in the table)
     */
    private int size;
    /**
     * Check modify for safe-fast
     */
    private int modCount;

    /**
     * Default constructor. Set up default values.
     */
    @SuppressWarnings({"rawtypes", "unchecked"}) //skip warnings
    public SimpleHashMap() {
        //initialize table with capacity - DEFAULT_INITIAL_CAPACITY
        table = (Node<K, V>[]) new Node[DEFAULT_INITIAL_CAPACITY];
        //set up threshold
        threshold = (int) (DEFAULT_LOAD_FACTORY * DEFAULT_INITIAL_CAPACITY);
        //set default capacity
        capacity = DEFAULT_INITIAL_CAPACITY;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<>() {
            private int index = 0; //current index based on position
            private int count = 0; //count element that was found
            private int change = modCount; //use for fail-fast checking
            private Node<K, V> element = null; //current element

            @Override
            public boolean hasNext() {
                if (change != modCount) {
                    throw new ConcurrentModificationException();
                }
                return count < size || (element != null && element.next != null);
            }

            @Override
            public Entry<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                if (element != null && element.next != null) {
                    element = element.next;
                } else {
                    while (count < size) {
                        element = table[index++];
                        if (element != null) {
                            count++;
                            break;
                        }
                    }
                }

                return element;
            }
        };
    }

    /**
     * Node is unit for hash table
     *
     * @param <K> any href type
     * @param <V> andy href type
     */
    static class Node<K, V> implements Entry<K, V> {
        int hash;
        K key;
        V value;
        Node<K, V> next;

        public Node(int hash, K key, V value) {
            this.value = value;
            this.key = key;
            this.hash = hash;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }
    }

    @Override
    public void put(K key, V value) {
        if (size == threshold) {
            resize();
        }
        size += put(this.table, this.capacity, key, value);
        modCount++;
    }


    /**
     * This is general method for put new element in the table.
     * <p>
     * <p>
     * Logic Steps (LS):
     * 1) If key not found then add it in the table. Go to step 5)
     * 2) If key exist then try to check on equals. Go to step 3)
     * 3) If key equals then replace exist value. Go to step 5)
     * 4) If key not equals then (collision) then bind with exist*;
     * 5) End
     * <p>
     * *4)But first go over all linked list and check them on equals if
     * found then 3) if not then 4) and 5)
     *
     * @param table    hash table
     * @param capacity current capacity
     * @param key      key
     * @param value    value
     * @return size of occupied cells from table 1 or 0. For example [][1][][3][][5][] size equals 3
     * \         \
     * [1]-[1]    [5]   linked list
     */
    private int put(Node<K, V>[] table, int capacity, K key, V value) {
        int hash = hash(key);                        //optimization hashCode()
        int size = 0;                                //how mach cells was occupied. It will 0 or 1
        int n = (capacity - 1) & hash;               //lead hash through filter mask and get the cell
        Node<K, V> element = table[n];                //get element from cell
        if (element == null) {                       //check element if not exist then insert |LS: 1)|
            table[n] = new Node<>(hash, key, value); //insert new element then: size++ then exit
            size++;
        } else if (element.hash == hash               //compare hash code and
                && Objects.equals(key, element.key)) { // compare equals if true then change value |LS: 3)|
            element.value = value;                     // change value then exit
        } else {
            while ((element = element.next) != null) {  //check in every node |LS: *4)|
                if (element.hash == hash && Objects.equals(key, element.key)) { //if the same
                    element.value = value;                                      //then replace value then exit
                    break;
                }
            }
            if (element == null) {                                // If above cycle arrived to end then: |LS: 4)|
                Node<K, V> newNode = new Node<>(hash, key, value); // covert new element to new node
                newNode.next = table[n];                            // bind tail's new element with old element
                table[n] = newNode;                                 // replace old element
            }
        }
        return size;                                                //return size of new cell 0 or 1
    }

    /**
     * Resize hash table when arrived threshold
     * arrange all elements in the table over again
     */
    private void resize() {
        int size = 0;
        int capacity = this.capacity << 1;  //change size. For example before 16 after 32
        if (capacity <= MAX_CAPACITY) {     //max capacity, 2^30 = 1073741824
            @SuppressWarnings({"rawtypes", "unchecked"}) //skip warnings
                    Node<K, V>[] tmp = (Node<K, V>[]) new Node[capacity];  //create tmp array nodes
            for (int i = 0; i < this.capacity; i++) { // fill new array tmp
                Node<K, V> element = table[i];
                if (element != null) {
                    size += put(tmp, capacity, element.key, element.value); //add to table
                    while (element.next != null) {                  //also try to split node and add to table
                        element = element.next;
                        size += put(tmp, capacity, element.key, element.value);
                    }
                }
            }
            threshold = (int) (DEFAULT_LOAD_FACTORY * capacity);
            this.capacity = capacity;
            table = tmp;
            this.size = size;
        }
    }

    @Override
    public V get(K key) {
        int hash = hash(key);
        Node<K, V> element = table[(capacity - 1) & hash];
        if (element != null && !Objects.equals(key, element.key)) {
            while (element.next != null) {
                element = element.next;
                if (hash == element.hash && Objects.equals(key, element.key)) {
                    break;
                }
            }
        }

        return element == null ? null : element.value;
    }

    @Override
    public V remove(K key) {
        int hash = hash(key);
        Node<K, V> element = table[(capacity - 1) & hash];
        Node<K, V> before;
        V value = element == null ? null : element.value;
        if (element != null && !Objects.equals(key, element.key)) {
            while (element.next != null) {
                before = element;
                element = element.next;
                if (hash == element.hash && Objects.equals(key, element.key)) {
                    value = element.value;
                    before.next = element.next;
                    break;
                }
            }
        } else {
            table[(capacity - 1) & hash] = null;
            size--;
        }
        if (value != null) {
            modCount++;
        }
        return value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Entry<K, V> e : this) {
            sb.append(e.getKey());
            sb.append("=");
            sb.append(e.getValue());
            sb.append(", ");
        }
        sb.replace(sb.length() - 2, sb.length(), "]");
        return sb.toString();
    }

    /**
     * Optimization hash code for minimization collisions.
     * Got from original HashMap
     *
     * @param key optimization
     * @return modified K::hashCode
     */
    private int hash(K key) {
        return key == null ? 0 : key.hashCode() ^ key.hashCode() >>> 16;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
