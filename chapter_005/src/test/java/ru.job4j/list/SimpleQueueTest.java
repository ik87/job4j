package ru.job4j.list;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SimpleQueueTest {

    SimpleQueue<Integer> queue;

    @Before
    public void init() {
        queue = new SimpleQueue<>();
        queue.push(1);
        queue.push(2);
        queue.push(3);
    }

    @Test
    public void whenPushThenPushed() {
        assertThat(queue.getCount(), is(3));
    }

    @Test
    public void whenPopThenPopped() {
        Assert.assertThat(queue.pop(), is(1));
        Assert.assertThat(queue.pop(), is(2));
        Assert.assertThat(queue.pop(), is(3));
        assertThat(queue.getCount(), is(0));
    }
    @Test
    public void whenPopAndPushThenSame() {
        StringBuilder sb = new StringBuilder();
        queue.pop(); //pop 1
        queue.pop(); //pop 2
        queue.push(8);
        queue.push(9);
        while (queue.getCount() > 0) {
            sb.append(queue.pop());
        }
        assertThat(sb.toString(), is("389"));
    }
}