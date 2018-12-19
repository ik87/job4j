package ru.job4j.extra;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TasksTest {

    private final int[] arr1 = {1, 2, 3, 4, 5};
    private final int[] arr2 = {6, 7, 8, 9, 10};
    private final Tasks tasks = new Tasks();


    @Test
    public void whenArraySortedThenTrue() {
        boolean result = tasks.checkSortedArray(arr1);
        assertThat(result, is(true));
    }

    @Test
    public void whenTwoArraysConcatThenThirdArray() {
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] result = tasks.concatArrays(arr1, arr2);
        assertThat(result, is(expected));
    }

}
