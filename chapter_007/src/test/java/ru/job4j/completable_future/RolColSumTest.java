package ru.job4j.completable_future;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.core.Is.is;
import static ru.job4j.completable_future.RolColSum.Sums;

import static org.junit.Assert.*;

public class RolColSumTest {

    int[][] matrix = new int[3][3];

    @Before
    public void init() {
        int i = 1;
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix.length; x++) {
                matrix[y][x] = i++;
            }
        }
    }

    @Test
    public void whenSumThenOK() {
        Sums[] sums = RolColSum.sum(matrix);
        assertThatSumMatrix(sums);
    }

    @Test
    public void whenAsyncSumThenOK() throws ExecutionException, InterruptedException {
        Sums[] sums = RolColSum.asyncSum(matrix);
        assertThatSumMatrix(sums);
    }

    void assertThatSumMatrix(Sums[] sums) {
        assertThat(sums[0].getRowSum(), is(6));
        assertThat(sums[1].getRowSum(), is(15));
        assertThat(sums[2].getRowSum(), is(24));

        assertThat(sums[0].getColSum(), is(12));
        assertThat(sums[1].getColSum(), is(15));
        assertThat(sums[2].getColSum(), is(18));
    }

}