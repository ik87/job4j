package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class FindLoopTest {

    @Test
    public void whenArrayHasElement5Then0() {
        FindLoop finder = new FindLoop();
        int[] input = new int[]{5, 10, 3};
        int value = 5;
        int result = finder.indexOf(input, value);
        int expect = 0;
        assertThat(result, is(expect));
    }

    @Test
    public void whenArrayHasElement6ThenMinus1() {
        FindLoop finder = new FindLoop();
        int[] input = new int[]{5, 10, 3};
        int value = 6;
        int result = finder.indexOf(input, value);
        int expect = -1;
        assertThat(result, is(expect));
    }
}