package ru.job4j.extra;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 1.0
 * @since 19.12.2018
 */
public class Tasks {

    /* Задача 1. Как узнать, является ли массив отсортированным*/

    /**
     * Check sorted array
     *
     * @param arr array
     * @return result
     */
    public boolean checkSortedArray(int[] arr) {
        int num = arr[0];
        boolean flag = true;
        for (int i = 1; i < arr.length; i++) {
            if (num <= arr[i]) {
                num = arr[i];
            } else {
                flag = false;
                break;
            }
        }
        return flag;
    }

    /*
    Задача 2. Даны два  отсортированных массива,
    нужно получить третий отсортированный
    состоящий из двух отсортированных.
    Прибегать к доп. сортировке нельзя
    */

    /**
     * Concat two arrays
     *
     * @param a array less arr
     * @param b array more arr
     * @return result concat
     */
    public int[] concatArrays(int[] a, int[] b) {
        if (a[0] > b[0]) {
            int[] tmp = b;
            b = a;
            a = tmp;
        }
        int[] c = new int[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }
}
