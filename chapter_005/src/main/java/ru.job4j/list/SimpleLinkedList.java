package ru.job4j.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Simple linked list, index starts from end;
 * Also it implements "fail-fast" iterator
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class SimpleLinkedList<E> implements SimpleList<E> {

    private int size;
    private Integer modCount = 0;
    private Node<E> first;

    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
        }
    }

    @Override
    public E get(int index) {
        Node<E> result = this.first;
        for (int i = 0; i < index; i++) {
            result = result.next;
        }
        return result.data;
    }

    @Override
    public void add(E data) {
        Node<E> newItem = new Node<>(data);
        newItem.next = this.first; //  next <- null
        this.first = newItem; // first is a newItem
        size++;
        modCount++;
    }

    @Override
    public E delete() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        E data = this.first.data;
        this.first = this.first.next;
        size--;
        modCount++;
        return data;
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {

        return new Iterator<>() {
            private Node<E> node = first;
            private final Integer change = modCount;

            @Override
            public boolean hasNext() {
                if (!change.equals(modCount)) {
                    throw new ConcurrentModificationException();
                }
                return !Objects.isNull(node);
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E data = node.data;
                node = node.next;
                return data;
            }
        };
    }
}