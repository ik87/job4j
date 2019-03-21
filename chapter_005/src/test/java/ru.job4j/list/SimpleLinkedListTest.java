package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class SimpleLinkedListTest {
    private SimpleLinkedList<Integer> list;

    @Before
    public void beforeTest() {
        list = new SimpleLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
    }

    @Test
    public void whenAddThreeElementsThenUseGetOneResultTwo() {
        assertThat(list.get(1), is(2));
    }

    @Test
    public void whenAddThreeElementsThenUseGetSizeResultThree() {
        assertThat(list.count(), is(3));
    }

    @Test
    public void whenDeleteSecondElementsThenUseGetOneResultTwo() {
        assertThat(list.delete(), is(3));
        assertThat(list.get(1), is(1));
    }

    @Test
    public void whenDeleteAllElementsThenSizeZero() {
        assertThat(list.delete(), is(3));
        assertThat(list.delete(), is(2));
        assertThat(list.delete(), is(1));
        assertThat(list.count(), is(0));
    }

    @Test
    public void whenIterateThenGetAllContent() {
        StringBuilder sb = new StringBuilder();
        for (Integer element : list) {
            sb.append(element);
        }
        assertThat(sb.toString(), is("321"));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenAddDuringIterateThenException() {
        for (var element : list) {
            if (element < 3) {
                list.add(3);
            }
        }
    }
}