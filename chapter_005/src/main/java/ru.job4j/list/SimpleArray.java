package ru.job4j.list;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Example simply array with dynamic capacity
 * Also it implements "fail-fast" iterator
 *
 * @param <E> any reference type
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class SimpleArray<E> implements SimpleList<E> {
    private final static int DEFAULT_CAPACITY = 3;
    private Object[] container;
    private Integer count = 0;
    private Integer modCount = 0;

    public SimpleArray() {
        container = new Object[DEFAULT_CAPACITY];
    }

    /**
     * check "count" and "container" if they size are equal then make resize
     */
    private void checkSize() {
        if (count.equals(this.container.length)) {
            grove();
        }
    }

    /**
     * increase size "container" by DEFAULT_CAPACITY
     */
    private void grove() {
        Object[] tmp = new Object[container.length + DEFAULT_CAPACITY];
        System.arraycopy(container, 0, tmp, 0, container.length);
        container = tmp;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private Integer index = 0;
            private final Integer change = modCount;

            @Override
            public boolean hasNext() {
                if (!change.equals(modCount)) {
                    throw new ConcurrentModificationException();
                }
                return index < count;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (E) container[index++];
            }
        };
    }

    @Override
    public void add(E data) {
        checkSize();
        container[count++] = data;
        modCount++;
    }

    @Override
    public E delete() {
        return null;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public E get(int index) {
        if (index > count) {
            throw new IndexOutOfBoundsException();
        }
        return (E) container[index];
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(container, count));
    }
}
