package ru.job4j.generics;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Example simple generic collection with fixed size
 *
 * @param <T> any type
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 0.1
 * @since 18.03.2019
 */
public class SimpleArray<T> implements Iterable<T> {
    private Object[] data;
    private Integer count = 0;

    public SimpleArray(int size) {
        this.data = new Object[size];
    }

    /**
     * add element
     * @param element any element
     * @throws ArrayStoreException if arrived max size for array
     */
    public void add(T element) {
        if (count == data.length) {
            throw new ArrayStoreException();
        }
        data[count++] = element;
    }

    /**
     * get element by index
     * @param index index element
     * @return T element
     */
    public T get(int index) {
        return (T) data[index];
    }

    /**
     * replace element in the index position
     * @param index position for new element
     * @param element any element
     * @throws IndexOutOfBoundsException if index more then array length or less then 0
     */
    public void set(int index, T element) {
        if (index >= count && index < 0) {
            throw new IndexOutOfBoundsException();
        }
        data[index] = element;
    }

    /**remove element and decrement 'count'
     *
     * @param index index remove element
     * @throws IndexOutOfBoundsException if index more then array length or less then 0
     */
    public void remove(int index) {
        if (index >= count && index < 0) {
            throw new IndexOutOfBoundsException();
        }
        System.arraycopy(data, index + 1, data, index, data.length - 1 - index);
        count--;

    }

    /**
     * get current size element in array
     * @return count
     */
    public Integer getCount() {
        return count;
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(data, count));
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < count;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) data[index++];
            }
        };
    }
}
