package ru.job4j.tree;

import java.util.Optional;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * This is Simple version Tree
 *
 * @param <E> any comparable type
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 0.1
 * @since $Id$
 */
public class Tree<E extends Comparable<E>> implements SimpleTree<E> {


    private Node<E> root;
    private int modCount;

    @Override
    public boolean add(E parent, E child) {
        boolean result = false;
        Optional<Node<E>> pr = findBy(parent);
        Optional<Node<E>> ch = findBy(child);

        if (ch.isEmpty()) {
            if (pr.isEmpty()) {
                Node<E> node = new Node<>(parent);
                node.add(new Node<>(child));
                root.add(node);
            } else {
                pr.get().add(new Node<>(child));
            }
            result = true;
            modCount++;
        } else if (pr.isEmpty()) {
            root.add(new Node<>(parent));
            result = true;
            modCount++;
        }
        return result;
    }

    @Override
    public boolean isBinary() {
        boolean result = true;
        Node<E> rsl = null;
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        if (this.root.leaves().size() <= 2) {
            while (!data.isEmpty()) {
                rsl = data.poll();
                for (Node<E> e : rsl.leaves()) {
                    if (e.leaves().size() > 2) {
                        result = false;
                        break;
                    }
                    data.offer(e);
                }
            }
        } else {
            result = false;
        }
        return result;
    }

    /**
     * Constructor
     *
     * @param value first node
     */
    public Tree(E value) {
        root = new Node<>(value);
    }


    @Override
    public Optional<Node<E>> findBy(E value) {
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (el.eqValue(value)) {
                rsl = Optional.of(el);
                break;
            }
            for (Node<E> child : el.leaves()) {
                data.offer(child);
            }
        }
        return rsl;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private Node<E> current = null;
            private Queue<Node<E>> data = new LinkedList<>() {
                {
                    offer(root);
                }
            };
            private int change = modCount;

            @Override
            public boolean hasNext() {
                if (change != modCount) {
                    throw new ConcurrentModificationException();
                }
                return !data.isEmpty();
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                current = data.poll();

                E val = current.getValue();
                if (!current.leaves().isEmpty()) {
                    for (Node<E> el : current.leaves()) {
                        data.offer(el);
                    }
                }
                return val;
            }
        };
    }
}
