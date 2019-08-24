package ru.job4j.concurrency_collections.list;

import org.junit.Test;
import ru.job4j.list.SimpleList;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class ConcurrencyArrayListTest {
    /**
     * Checking stability against ConcurrentModificationException
     *
     * @throws InterruptedException
     */
    @Test
    public void whenAddAndIterateThenSuccess() throws InterruptedException {
        SimpleList<Integer> simpleList = new ConcurrencyArrayList<>();
        Runnable runnableA = () -> {
            for (int i = 0; i < 10; i++) {
                simpleList.add(i);
                try {
                    Thread.currentThread().sleep(100L);
                } catch (InterruptedException e) {

                }
            }
        };
        Thread threadA = new Thread(runnableA);
        threadA.start();

        Thread.currentThread().sleep(100L);
        for (var elem : simpleList) {
            Thread.currentThread().sleep(100L);
        }
    }
}