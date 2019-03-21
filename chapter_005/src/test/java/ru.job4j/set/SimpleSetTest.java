package ru.job4j.set;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 0.1
 */
public class SimpleSetTest {
    @Test
    public void whenAddThenDoesntContainTheSame() {
        SimpleSet<Integer> set = new SimpleSet<>();
        Integer[] numbers = {1, 2, 1, 3, 4, 4, 5, 3, 2, 1};
        for (int n : numbers) {
            set.add(n);
        }
        assertThat(set.toString(), is("[1, 2, 3, 4, 5]"));
    }
}