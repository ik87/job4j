package ru.job4j.tree;

import java.util.Optional;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @since 0.1
 * @param <E> any comparable object
 */
public interface SimpleTree<E extends Comparable<E>> extends Iterable<E> {
    /**
     * Добавить элемент child в parent.
     * Parent может иметь список child.
     * @param parent parent.
     * @param child child.
     * @return if added then true
     */
    boolean add(E parent, E child);

    Optional<Node<E>> findBy(E value);

    /**
     * Check if tree is binary
     * @return true if yes
     */
    boolean isBinary();
}
