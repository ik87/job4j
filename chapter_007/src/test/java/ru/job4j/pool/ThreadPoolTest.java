package ru.job4j.pool;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ThreadPoolTest {


    @Test
    public void whenIncrementThenGetTen() throws InterruptedException {
        final int[] count = {0};
        int expected = 10;
        int size = Runtime.getRuntime().availableProcessors();
        ThreadPool threadPool = new ThreadPool(size, size);

        for (int i = 0; i < 10; i++) {
            threadPool.work(() -> count[0]++);
        }

        while (threadPool.countOfTasks() != 0) {
            Thread.sleep(10);
        }

        threadPool.shutdown();

        assertThat(expected, is(count[0]));
    }

}