package ru.job4j.producer_consumer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class SimpleBlockingQueueTest {


    @Test
    public void whenPutFiveElementsThenGetThem() throws InterruptedException {
        SimpleBlockingQueue<Integer> blockingQueue = new SimpleBlockingQueue<>(3);
        List<Integer> result = new ArrayList<>();
        final int count = 5;

        Thread[] producerThread = new Thread[count];
        Thread[] consumerThread = new Thread[count];

        for (int i = 0; i < count; i++) {
            (producerThread[i] = new Thread(new ProducerThread(blockingQueue))).start();
            (consumerThread[i] = new Thread(new ConsumerThread(blockingQueue, result))).start();
        }

        for (int i = 0; i < count; i++) {
            producerThread[i].join();
            consumerThread[i].join();
        }
        
        

    }


    class ConsumerThread implements Runnable {
        private SimpleBlockingQueue<Integer> simpleBlockingQueue;
        private List<Integer> elements;

        public ConsumerThread(SimpleBlockingQueue<Integer> simpleBlockingQueue, List<Integer> elements) {
            this.simpleBlockingQueue = simpleBlockingQueue;
            this.elements = elements;
        }

        public List<Integer> getElements() {
            return elements;
        }

        @Override
        public void run() {
            try {
                elements.add(simpleBlockingQueue.pool());
            } catch (InterruptedException e) {
                System.out.print(e.getStackTrace());
            }
        }

    }

    class ProducerThread implements Runnable {
        private SimpleBlockingQueue<Integer> simpleBlockingQueue;

        public ProducerThread(SimpleBlockingQueue<Integer> simpleBlockingQueue) {
            this.simpleBlockingQueue = simpleBlockingQueue;
        }

        @Override
        public void run() {
            try {
                simpleBlockingQueue.offer(1);
            } catch (InterruptedException e) {
                System.out.print(e.getStackTrace());
            }
        }
    }

}