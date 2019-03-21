package ru.job4j.set;

import ru.job4j.list.SimpleArray;
import ru.job4j.list.SimpleList;

import java.util.Iterator;
/**
 * Example simply set based on the SimpleArray list
 *
 * @param <E> any reference type
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class SimpleSet<E> implements Iterable<E> {
    SimpleList<E> container = new SimpleArray<>();

    /**
     * add any reference type element
     * check if it isn't unique then skip
     * @param element element
     */
    public void add(E element) {
        boolean has = false;
        for (E e : container) {
            if (e.equals(element)) {
                has = true;
                break;
            }
        }
        if (!has) {
            container.add(element);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return container.iterator();
    }

    @Override
    public String toString() {
        return container.toString();
    }
}
