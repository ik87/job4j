package ru.job4j.fork_join_pool;

import java.util.concurrent.ForkJoinPool;

/**
 * Recursive element search
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 1.0
 * @since 05.10.2020
 */

public class RecursiveSearch {
    public static int lineFinding(int[] arr, int el) {
        return search(arr, 0, arr.length, el);
    }

    /**
     * Recursive method for search element
     *
     * @param arr  array of integers
     * @param from start search from
     * @param to   end search
     * @param el   searching element
     * @return found element index or -1 (if not found)
     */
    public static int search(int[] arr, int from, int to, int el) {
        if (to - from <= 10) {
            return lineSearch(arr, from, to, el);
        }

        int midl = (to + from) / 2;
        return Math.max(
                search(arr, from, midl, el),
                search(arr, midl + 1, to, el)
        );

    }

    /**
     * Line search method
     *
     * @param arr  array of integers
     * @param from start search from
     * @param to   end search
     * @param el   searching element
     * @return found element index or -1 (if not found)
     */
    public static int lineSearch(int[] arr, int from, int to, int el) {
        int result = -1;
        while (from < to) {
            if (el == arr[from]) {
                result = from;
                break;
            }
            from++;
        }
        return result;
    }

    public static int search(int[] arr, int el) {
        return search(arr, 0, arr.length, el);
    }
}
