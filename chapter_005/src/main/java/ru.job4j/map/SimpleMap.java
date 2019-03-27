package ru.job4j.map;



/**
 * This is simplified version of java.util.Map
 *
 * @param <K> key any key
 * @param <V> value any value
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @since 0.1
 */
public interface SimpleMap<K, V> extends Iterable<SimpleMap.Entry<K, V>> {
    /**
     * put new K and V objects
     *
     * @param key   any key
     * @param value any value
     */
    void put(K key, V value);

    /**
     * Get V value by K key
     *
     * @param key any key
     * @return value
     */
    V get(K key);

    /**
     * Remove element from collection
     *
     * @param key any key
     * @return get removed V or null
     */
    V remove(K key);

    /**
     * If map is empty then true
     * @return map is empty then true
     */
    boolean isEmpty();

    /**
     * This is simplified version Entry
     *
     * @param <K>
     * @param <V>
     */
    interface Entry<K, V> {
        /**
         * Returns the key corresponding to this entry
         *
         * @return the key corresponding to this entry
         */
        K getKey();

        /**
         * Returns the value corresponding to this entry
         *
         * @return the value corresponding to this entry
         */
        V getValue();
    }
}
