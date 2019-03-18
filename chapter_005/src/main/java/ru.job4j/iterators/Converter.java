package ru.job4j.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Converter  iterator of iterators to single iterator
 *
 * @author Kosolpaov Ilya (d_dexter@mail.ru)
 * @version 0.2
 * @since 17.03.2019
 */
public class Converter {
    Iterator<Integer> iterator = null;

    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        iterator = it.next();
        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                process();
                return iterator.hasNext();
            }

            @Override
            public Integer next() {
                process();
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return iterator.next();
            }

            /**
             * processing next int
             */
            private void process() {
                while (it.hasNext()) {
                    if (!iterator.hasNext()) {
                        iterator = it.next();
                    } else {
                        break;
                    }
                }
            }
        };

    }
}
