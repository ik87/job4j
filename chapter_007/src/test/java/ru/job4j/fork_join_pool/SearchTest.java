package ru.job4j.fork_join_pool;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SearchTest {

    @Test
    public void whenElementWasFoundParallelThenGetIndex() {
        int[] arr = new int[100_000_000];
        arr[100_000_000 / 2 + 234] = 3;
        int result = ParallelSearch.search(arr, 3);
        assertThat(result, is(100_000_000 / 2 + 234));
    }

    @Test
    public void whenElementWasFoundRecursiveThenGetIndex() {
        int[] arr = new int[100_000_000];
        arr[100_000_000 / 2 + 234] = 3;
        int result = RecursiveSearch.search(arr, 3);
        assertThat(result, is(100_000_000 / 2 + 234));
    }

}