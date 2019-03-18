package ru.job4j.generics;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 */
public class SimpleArrayTest {

    @Test
    public void whenAddThenSame() {
        SimpleArray<Integer> simpleArray = new SimpleArray<>(15);
        /**
         * add 1-5
         */
        for (int i = 0; i < 5; i++) {
            simpleArray.add(i + 1);
        }
        assertThat(simpleArray.toString(), is("[1, 2, 3, 4, 5]"));
        assertThat(simpleArray.getCount(), is(5));
    }

    @Test
    public void whenGetElementThen3() {
        SimpleArray<Integer> simpleArray = new SimpleArray<>(15);
        /**
         * add 1-5
         */
        for (int i = 0; i < 5; i++) {
            simpleArray.add(i + 1);
        }

        assertThat(simpleArray.get(2), is(3));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenGetElementThenException() {
        SimpleArray<Integer> simpleArray = new SimpleArray<>(1);
        simpleArray.add(3);
        simpleArray.get(2);
    }

    @Test
    public void checkCount() {
        SimpleArray<Integer> simpleArray = new SimpleArray<>(5);
        assertThat(simpleArray.getCount(), is(0));
        simpleArray.add(3);
        assertThat(simpleArray.getCount(), is(1));
    }

    @Test
    public void whenRemoveOneElementThenEmpty() {
        SimpleArray<Integer> simpleArray = new SimpleArray<>(1);
        simpleArray.add(1);
        simpleArray.remove(0);
        Assert.assertThat(simpleArray.toString(), is("[]"));
        Assert.assertThat(simpleArray.getCount(), is(0));
    }

    @Test
    public void whenRemoveInTheMiddleThenRemoved() {
        SimpleArray<Integer> simpleArray = new SimpleArray<>(15);
        /**
         * add 1-5
         */
        for (int i = 0; i < 5; i++) {
            simpleArray.add(i + 1);
        }
        simpleArray.remove(2);
        Assert.assertThat(simpleArray.toString(), is("[1, 2, 4, 5]"));
        Assert.assertThat(simpleArray.getCount(), is(4));
    }

    @Test
    public void whenSetNewElementThenChanged() {
        SimpleArray<Integer> simpleArray = new SimpleArray<>(15);
        /**
         * add 1-5
         */
        for (int i = 0; i < 5; i++) {
            simpleArray.add(i + 1);
        }
        simpleArray.set(3, 22);
        assertThat(simpleArray.toString(), is("[1, 2, 3, 22, 5]"));
    }

    @Test
    public void checkIterator() {
        SimpleArray<Integer> simpleArray = new SimpleArray<>(15);
        /**
         * add 1-5
         */
        for (int i = 0; i < 5; i++) {
            simpleArray.add(i + 1);
        }
        Iterator<Integer> it = simpleArray.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
        assertThat(it.next(), is(4));
        assertThat(it.next(), is(5));
        assertThat(it.hasNext(), is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void checkIteratorException() {
        SimpleArray<Integer> simpleArray = new SimpleArray<>(1);
        simpleArray.add(1);
        Iterator<Integer> it = simpleArray.iterator();
        it.next();
        it.next();
    }
}
