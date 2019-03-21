package ru.job4j.list;

/**
 * This is the simple stack (LIFO) is based on the linked list
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @since $Id$
 * @version 0.1
 * @param <E> any reference type
 * */
public class SimpleStack<E> {
    SimpleList<E> container = new SimpleLinkedList<>();

    /**
     * an element put on the top stack
     * @param element some element
     */
    public void push(E element) {
        container.add(element);
    }

    /**
     * get a top element
     * @return top element
     */
    public E pop() {
        return container.delete();
    }

    /**
     * get capacity
     * @return size
     */
    public int count() {
        return container.count();
    }

    /**
     * check is capacity
     * @return result
     */
    public boolean isEmpty() {
        return container.count() == 0;
    }
}
