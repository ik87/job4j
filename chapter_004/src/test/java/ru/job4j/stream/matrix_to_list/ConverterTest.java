package ru.job4j.stream.matrix_to_list;

import java.util.List;
import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class ConverterTest {

    @Test
    public void whenMatrixIntThenList() {

        Integer[][] matrix = {
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20}
        };
        List<Integer> result = new Converter().matrixIntegerToList(matrix);
        Integer[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};


        assertThat(result, is(Arrays.asList(expected)));
    }
}
