package ru.job4j.iterators;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Example Iterator for Even nums;
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 0.2
 * @since 17.03.2019
 */
public class EvenNumbersIterator implements Iterator {
    private int[] data;
    private Integer index = 0;

    public EvenNumbersIterator(int[] data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        for (; data.length > index; index++) {
            if (data[index] % 2 == 0) {
                break;
            }
        }
        return index < data.length;
    }

    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        return data[index++];
    }

}