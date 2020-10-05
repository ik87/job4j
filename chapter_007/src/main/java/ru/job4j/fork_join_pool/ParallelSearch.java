package ru.job4j.fork_join_pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Parallel (ForkJoinPool) element search
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 1.0
 * @since 5.10.2020
 */
public class ParallelSearch extends RecursiveTask<Integer> {
    private final int[] arr;
    private final int from;
    private final int to;
    private final int el;

    public ParallelSearch(int[] arr, int from, int to, int el) {
        this.arr = arr;
        this.from = from;
        this.to = to;
        this.el = el;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return RecursiveSearch.lineSearch(arr, from, to, el);
        }
        int mid = (from + to) / 2;
        ParallelSearch leftSearch = new ParallelSearch(arr, from, mid, el);
        ParallelSearch rightSearch = new ParallelSearch(arr, mid + 1, to, el);

        leftSearch.fork();
        rightSearch.fork();

        int left = leftSearch.join();
        int right = rightSearch.join();

        return Math.max(left, right);
    }

    public static int search(int[] arr, int el) {
        int countThread = Runtime.getRuntime().availableProcessors();
        ForkJoinPool forkJoinPool = new ForkJoinPool(countThread);
        return forkJoinPool.invoke(new ParallelSearch(arr, 0, arr.length, el));
    }
}
