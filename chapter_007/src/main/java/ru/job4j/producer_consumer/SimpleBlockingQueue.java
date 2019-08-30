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
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {
    private static final Logger LOG = LogManager.getLogger(SimpleBlockingQueue.class.getName());
    @GuardedBy("lock")
    private Queue<T> queue = new LinkedList<>();
    private final Object lock = new Object();

    private int capacity;

    SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Accept some elements type T,
     * if size queue more capacity then thread become waiting
     *
     * @param value any type T
     * @throws InterruptedException If happen interrupt thread
     */
    public void offer(T value) throws InterruptedException {
        synchronized (lock) {
            LOG.debug(Thread.currentThread().getName() + " offer");
            while (queue.size() > capacity) {
                LOG.debug(Thread.currentThread().getName() + " offer wait");
                lock.wait();
            }
            queue.offer(value);
            LOG.debug(Thread.currentThread().getName() + " offer notify");
            lock.notify();
        }
    }

    /**
     * Pool some elements type T,
     * if queue empty then thread become waiting
     *
     * @return some elements type T
     * @throws InterruptedException If happen interrupt thread
     */
    public T pool() throws InterruptedException {
        synchronized (lock) {
            LOG.debug(Thread.currentThread().getName() + " pool");
            while (queue.size() == 0) {
                LOG.debug(Thread.currentThread().getName() + " pool wait");
                lock.wait();
            }
            T element = queue.poll();
            LOG.debug(Thread.currentThread().getName() + " pool notify");
            lock.notify();
            return element;
        }
    }

    /**
     * @return size of queue
     */
    public int size() {
        return queue.size();
    }
}
