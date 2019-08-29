package ru.job4j.producer_consumer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @param <T>
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    private int capacity;

    public SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public void offer(T value) throws InterruptedException {
        synchronized (lock1) {
            capacity++;
            while (queue.size() > capacity) {
                lock1.wait();
            }
            queue.offer(value);
            if(queue.size() > 0) {
                lock2.notify();
            }
        }
    }

    public T pool() throws InterruptedException {
        synchronized (lock2) {
            while (queue.size() == 0) {
                lock2.wait();
            }
            T element = queue.peek();
            capacity--;
            if(capacity < queue.size()) {
                lock1.notify();
            }
            return element;
        }
    }
}
