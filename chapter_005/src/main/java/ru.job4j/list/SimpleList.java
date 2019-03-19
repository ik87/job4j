package ru.job4j.list;
/**
 * interface simple list
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public interface SimpleList<E> {
    /**
     * add data
     * @param data data
     */
    void add(E data);

    /**
     *delete first element
     * @return deleted element
     * @throws "NoSuchElementException if list is empty"
     */
    E delete();

    /**
     * get current capacity
     * @return size
     */
    int getCount();

    /**
     * get element by index
     * @param index index element
     * @return element
     */
    E get(int index);
}
