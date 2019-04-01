package ru.job4j.tree;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
/**
 * @author Petr Arsentev (parsentev@yandex.ru) & Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.2
 */
public class TreeTest {
    @Test
    public void when6ElFindLastThen6() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(4, 5);
        tree.add(6, 5);
        tree.add(5, 6);
        assertThat(
                tree.findBy(6).isPresent(),
                is(true)
        );
    }

    @Test
    public void when6ElFindNotExitThenOptionEmpty() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        assertThat(
                tree.findBy(7).isPresent(),
                is(false)
        );
    }

    @Test
    public void whenIterateThenIterated() {
        Tree<Integer> tree = new Tree<>(1);
        Integer[] expected = {1, 2, 3, 4, 5, 6};
        List<Integer> result = new ArrayList<>();
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(4, 5);
        tree.add(6, 5);
        tree.add(5, 6);
        for (Integer e : tree) {
            result.add(e);
        }
        assertThat(result, is(containsInAnyOrder(expected)));
    }
}