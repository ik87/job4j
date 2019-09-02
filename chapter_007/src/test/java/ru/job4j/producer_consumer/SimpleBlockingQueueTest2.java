package ru.job4j.producer_consumer;

import org.junit.Test;
import ru.job4j.concurrency_collections.list.ConcurrencyArrayList;

import java.util.stream.IntStream;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class SimpleBlockingQueueTest2 {
    @Test
    public void whenPutFiveElementsThenGetThem() throws InterruptedException {
        ConcurrencyArrayList<Integer> buffer = new ConcurrencyArrayList<>();
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        Thread producer = new Thread(() -> {
            IntStream.range(0, 5).forEach(
                    x -> {
                        try {
                            queue.offer(x);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            );
        });

        Thread consumer = new Thread(() -> {
            while (queue.size() != 0 || !Thread.currentThread().isInterrupted()) {
                try {
                    buffer.add(queue.poll());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer.toString(), is("[0, 1, 2, 3, 4]"));
    }

}
