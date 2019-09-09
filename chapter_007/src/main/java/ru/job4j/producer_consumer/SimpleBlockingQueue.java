package ru.job4j.producer_consumer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This is concurrency queue pattern Producer-Consumer
 *
 * @param <T>
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {
    private static final Logger LOG = LogManager.getLogger(SimpleBlockingQueue.class.getName());
    @GuardedBy("queue")
    private final Queue<T> queue = new LinkedList<>();

    private int capacity;

    public SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Accept some elements type T,
     * if size queue more capacity then thread become waiting
     *
     * @param value some element type T
     * @throws InterruptedException If happen interrupt thread
     */
    public void offer(T value) throws InterruptedException {
        synchronized (queue) {
            LOG.debug("{} offer", Thread.currentThread().getName());
            while (queue.size() > capacity) {
                LOG.debug("{} offer wait", Thread.currentThread().getName());
                queue.wait();
            }
            queue.offer(value);
            LOG.debug("{} offer notify", Thread.currentThread().getName());
            queue.notify();
        }
    }

    /**
     * Pool some elements type T,
     * if queue empty then thread become waiting
     *
     * @return some element type T
     * @throws InterruptedException If happen interrupt thread
     */
    public synchronized T poll() throws InterruptedException {
        synchronized (queue) {
            LOG.debug("{} poll", Thread.currentThread().getName());
            while (queue.size() == 0) {
                LOG.debug("{} poll wait", Thread.currentThread().getName());
                queue.wait();
            }
            T element = queue.poll();
            LOG.debug("{} poll notify", Thread.currentThread().getName());
            queue.notify();
            return element;
        }
    }

    /**
     * @return size of queue
     */
    public int size() {
        synchronized (queue) {
            return queue.size();
        }
    }
}
