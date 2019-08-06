package ru.job4j.concurrency_problem;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Эмуояция проблеммы - Race Condition
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $id$
 * @since 0.1
 */
public class RaceCondition {
    public static int HUNDRED = 100_000;

    public static void main(String[] args) throws InterruptedException {
        //Эталонный атомарный счетчик итераций
        AtomicInteger atomicInteger = new AtomicInteger();
        //Общие счетчик, которые буду увеличиваться параллельно, и в котором будет происходить Race Condition
        SharedState sharedState = new SharedState();
        //Создаем два потока, в каждый передаем общий счетчик и атомарный счетчик
        SomeThread run1 = new SomeThread(sharedState, atomicInteger);
        SomeThread run2 = new SomeThread(sharedState, atomicInteger);
        Thread th1 = new Thread(run1);
        Thread th2 = new Thread(run2);
        //Запускаем потоки
        th1.start();
        th2.start();
        //Ожидаем основным потоком завершение работы в дочерних потоков
        th1.join();
        th2.join();
        //Выводим результат
        System.out.printf("Thread1: %d\n", run1.getCount());
        System.out.printf("Thread2: %d\n", run2.getCount());
        System.out.printf("AtomicInt: %d\n", atomicInteger.get());

    }


    static class SomeThread implements Runnable {

        private SharedState someClass;
        private AtomicInteger atomicInteger;

        public SomeThread(SharedState someClass, AtomicInteger atomicInteger) {
            this.someClass = someClass;
            this.atomicInteger = atomicInteger;
        }
        /*
        Проблемма связанна с тем, что каждый поток пытается произвести инкриментацию общего рессурса.
        Так как операция инкреминтации сама по себе не атомарна,  возникают перезатирания
        что выдает не ожидаемый резултат.
        Решение:
        Если раскоментировать блок synchronized, то проблемма Race Condition будет решена, т.к  потоки не будут
        портить атомарность инкриминтации у друг друга.
         */

        @Override
        public void run() {
            while (atomicInteger.get() < HUNDRED) {
              //  synchronized (this) {
                    atomicInteger.incrementAndGet();
                    someClass.inc();
               // }
            }
        }

        public int getCount() {
            return someClass.getCount();
        }
    }
}

class SharedState {
    private int count = 0;

    public synchronized void inc() {
        count++;
    }

    int getCount() {
        return count;
    }
}
