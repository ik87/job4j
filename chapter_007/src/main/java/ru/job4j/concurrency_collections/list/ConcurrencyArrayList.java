package ru.job4j.concurrency_collections.list;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.list.SimpleArray;
import ru.job4j.list.SimpleList;

import java.util.Iterator;

/**
 * This is concurrency safe version of SimpleArray list
 *
 * @param <E>
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class ConcurrencyArrayList<E> implements SimpleList<E> {
    @GuardedBy("this")
    private SimpleList<E> simpleList;

    public ConcurrencyArrayList() {
        this.simpleList = new SimpleArray<>();
    }

    @Override
    public synchronized void add(E data) {
        this.simpleList.add(data);
    }

    @Override
    public synchronized E delete() {
        return this.simpleList.delete();
    }

    @Override
    public synchronized int count() {
        return copy(this.simpleList).count();
    }

    @Override
    public synchronized E get(int index) {
        return copy(this.simpleList).get(index);
    }

    @Override
    public synchronized Iterator<E> iterator() {
        return copy(this.simpleList).iterator();
    }

    private SimpleList<E> copy(SimpleList<E> list) {
        SimpleList<E> simpleList = new SimpleArray<>();
        for (var element : list) {
            simpleList.add(element);
        }
        return simpleList;
    }
}
