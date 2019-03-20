package ru.job4j.list;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class SimpleStackTest {

    SimpleStack<Integer> stack;

    @Before
    public void init() {
        stack = new SimpleStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
    }

    @Test
    public void whenPushThenPushed() {
        assertThat(stack.getCount(), is(3));
    }

    @Test
    public void whenPopThenPopped() {
        Assert.assertThat(stack.pop(), is(3));
        Assert.assertThat(stack.pop(), is(2));
        Assert.assertThat(stack.pop(), is(1));
        assertThat(stack.getCount(), is(0));
    }
}