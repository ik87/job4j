package ru.job4j.pool;

import ru.job4j.producer_consumer.SimpleBlockingQueue;

/**
 * This is core unit for ThreadPool engine.
 * It execute some task that was get from BlockingQueue
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Core extends Thread {
    private SimpleBlockingQueue<Runnable> tasks;

    public Core(SimpleBlockingQueue<Runnable> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Runnable runnable = tasks.poll();
                runnable.run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
