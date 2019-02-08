package ru.job4j.var_search;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PriorityQueueTest {
    @Test
    public void whenHigherPriority() {
        PriorityQueue queue = new PriorityQueue();
        queue.put(new Task("low", 5));
        queue.put(new Task("urgent", 1));
        queue.put(new Task("middle", 3));
        Task result = queue.take();
        assertThat(result.getDesc(), is("urgent"));
    }

    @Test
    public void whenHigherPriorityTwo() {
        PriorityQueue queue = new PriorityQueue();
        queue.put(new Task("One", 5));
        queue.put(new Task("Two", 1));
        queue.put(new Task("Three", 15));
        queue.put(new Task("Four", -4));
        queue.put(new Task("Five", 6));
        queue.put(new Task("Six", 7));
        queue.put(new Task("Seven", 12));
        var result = queue.take();
        assertThat(result.getDesc(), is("Four"));

    }
}
