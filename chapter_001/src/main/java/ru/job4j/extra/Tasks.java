package ru.job4j.extra;

import java.time.format.TextStyle;
import java.util.Arrays;

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

        int[] c = new int[a.length + b.length];
        int j = 0, h = 0, i = 0;

        /* Вариант 1, когда кол. элементов в a[] и b[] равное, додумался сам.
        int j = 0, h = 0, i = 0;
        for (; i < c.length - 1; i++) {
            c[i] = a[j] < b[h] ? a[j++] : b[h++];
        }
        if(a.length != j) {
            c[i] = a[j];
        } else {
            c[i] = b[h];
        }
        */

        // Вариант 2. работает с любым кол. элементов
        // подсмотрел на хабре
        while (j < a.length && h < b.length) {
            c[i++] = a[j] < b[h] ? a[j++] : b[h++];
        }
        if (j < a.length) {
            System.arraycopy(a, j, c, i, a.length - j);
        } else if (h < b.length) {
            System.arraycopy(b, h, c, i, b.length - h);
        }

        return c;
    }
}
