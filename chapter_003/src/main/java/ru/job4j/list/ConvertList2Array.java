package ru.job4j.list;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $0.1$
 * @since 0.1
 */
public class ConvertList2Array {
    public int[][] toArray(List<Integer> list, int rows) {
        int cells = (int) Math.ceil(list.size() / (double) rows);
        int i = 0;

        int[][] array = new int[rows][cells];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cells; x++) {
                if (i == list.size()) {
                    break;
                }
                array[y][x] = list.get(i++);
            }
        }
        return array;
    }

    public List<Integer> convert(List<int[]> list) {
        List<Integer> data = new ArrayList<>();
        for (int[] y : list) {
            for (int x : y) {
                data.add(x);
            }
        }
        return data;
    }
}
