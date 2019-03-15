package ru.job4j.iterators;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Example Iterator for Even nums;
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 0.1
 * @since 15.03.2019
 */
public class EvenNumbersIterator implements Iterator {
    private int[] data;
    private Integer index = 0;

    public EvenNumbersIterator(int[] data) {

        this.data = Arrays
                .stream(data)
                .filter(x -> x % 2 == 0)
                .toArray();
    }

    @Override
    public boolean hasNext() {
        return index < data.length;
    }

    @Override
    public Object next() {
        if (data.length == index) {
            throw new NoSuchElementException();
        }

        return data[index++];

    }
}
