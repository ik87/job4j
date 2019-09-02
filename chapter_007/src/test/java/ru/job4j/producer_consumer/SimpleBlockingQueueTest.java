package ru.job4j.producer_consumer;


import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;


public class SimpleBlockingQueueTest {


    @Test
    public void whenPutFiveElementsThenGetThem() throws InterruptedException {
        SimpleBlockingQueue<Integer> blockingQueue = new SimpleBlockingQueue<>(2);
        final int count = 5;

        Thread[] producerThread = new Thread[count];
        Thread[] consumerThread = new Thread[count];

        for (int i = 0; i < count; i++) {
            (producerThread[i] = new Thread(new ProducerThread(blockingQueue))).start();
            (consumerThread[i] = new Thread(new ConsumerThread(blockingQueue))).start();
        }

        for (int i = 0; i < count; i++) {
            producerThread[i].join();
            consumerThread[i].join();
        }

        assertThat(blockingQueue.size(), is(0));

    }


    class ConsumerThread implements Runnable {
        private SimpleBlockingQueue<Integer> simpleBlockingQueue;

        ConsumerThread(SimpleBlockingQueue<Integer> simpleBlockingQueue) {
            this.simpleBlockingQueue = simpleBlockingQueue;
        }

        @Override
        public void run() {
            try {
                simpleBlockingQueue.pool();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    class ProducerThread implements Runnable {
        private SimpleBlockingQueue<Integer> simpleBlockingQueue;

        ProducerThread(SimpleBlockingQueue<Integer> simpleBlockingQueue) {
            this.simpleBlockingQueue = simpleBlockingQueue;
        }

        @Override
        public void run() {
            try {
                simpleBlockingQueue.offer(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}