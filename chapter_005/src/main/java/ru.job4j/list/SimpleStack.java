package ru.job4j.list;

/**
 * This is the simple stack is based on the linked list
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @since $Id$
 * @version 0.1
 * @param <E> any reference type
 * */
public class SimpleStack<E> {
    SimpleList<E> container = new SimpleLinkedList<>();

    public void push(E element) {
        container.add(element);
    }

    public E pop() {
        return container.delete();
    }

    public int getCount() {
        return container.getCount();
    }
}
