package ru.job4j.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.producer_consumer.SimpleBlockingQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * This is easy example of ThreadPool engine
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class ThreadPool {
    private static final Logger LOG = LogManager.getLogger(ThreadPool.class.getName());
    /**
     * This is storage for tasks
     */
    private final SimpleBlockingQueue<Runnable> tasks;
    /**
     * This is storage Cores
     * If your PC has 4 cores then it storage 4 core objects
     */
    private final List<Core> cores = new ArrayList<>();

    /**
     * Constructor ThreadPool
     * Start ThreadPool engine
     *
     * @param countTasks count of tasks
     * @param countCores number the cores in your system
     */
    public ThreadPool(int countTasks, int countCores) {
        tasks = new SimpleBlockingQueue<>(countTasks);

        for (int i = 0; i < countCores; i++) {
            cores.add(new Core(tasks));
        }

        for (Thread core : cores) {
            core.start();
            LOG.debug("{} was launched", core.getName());
        }

    }

    /**
     * This method get job into SimpleBlockingQueue
     *
     * @param job some Runnable task
     */
    public void work(Runnable job) {
        if (cores.isEmpty()) {
            throw new IllegalStateException("There aren't any working cores");
        }
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            LOG.debug("work(): Program was interrupt");
        }
    }

    /**
     * Get count not processed tasks
     * @return count of tasks
     */
    public int countOfTasks() {
        return tasks.size();
    }

    /**
     * Stop ThreadPool engine
     */
    public void shutdown() {
        for (Thread core : cores) {
            core.interrupt();
            LOG.debug("{} was shutdown", core.getName());
        }
        cores.clear();
    }
}

