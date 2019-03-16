package ru.job4j.iterators;

import java.util.Iterator;

/**
 * Converter  iterator of iterators to single iterator
 *
 * @author Kosolpaov Ilya (d_dexter@mail.ru)
 * @version 0.1
 * @since 16.03.2019
 */
public class Converter {
    Iterator<Integer> iterator = null;

    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        iterator = it.next();
        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                if (it.hasNext() && !iterator.hasNext()) {
                    iterator = it.next();
                }
                return iterator.hasNext();
            }

            @Override
            public Integer next() {

                if (!iterator.hasNext()) {
                    iterator = it.next();
                }
                return iterator.next();
            }
        };

    }
}
