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
public class TurnTest {

    @Test
    public void whenTurnArrayWithEvenAmountOfElementsThenTurnedArray() {
        Turn turner = new Turn();
        int[] input = {4, 1, 6, 2};
        int[] result = turner.back(input);
        int[] expect = {2, 6, 1, 4};
        assertThat(result, is(expect));
    }

    @Test
    public void whenTurnArrayWithOddEvenAmountOfElementsThenTurnArray() {
        Turn turner = new Turn();
        int[] input = {1, 2, 3, 4, 5};
        int[] result = turner.back(input);
        int[] expect = {5, 4, 3, 2, 1};
        assertThat(result, is(expect));
    }
}