package ru.job4j.coffee_machine;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CoffemachineTest {
    private Coffemachine coffemachine = new Coffemachine();

    @Test
    public void checkExchangeOne() {
        int[] result = coffemachine.changes(30, 14);
        int[] expected = {10, 5, 1};
        assertThat(result, is(expected));
    }

    @Test
    public void checkExchangeTwo() {
        int[] result = coffemachine.changes(65, 34);
        int[] expected = {10, 10, 10, 1};
        assertThat(result, is(expected));
    }

    @Test
    public void checkExchangeThree() {
        int[] result = coffemachine.changes(66, 34);
        int[] expected = {10, 10, 10, 2};
        assertThat(result, is(expected));
    }

    @Test
    public void checkExchangeFour() {
        int[] result = coffemachine.changes(66, 66);
        assertThat(result.length, is(0));
    }

}
