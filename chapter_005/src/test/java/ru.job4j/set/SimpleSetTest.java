package ru.job4j.set;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.core.Is.is;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 0.1
 */
public class SimpleSetTest {
    private SimpleSet<Integer> set;

    @Before
    public void init() {
        set = new SimpleSet<>();
        Integer[] numbers = {1, 2, 1, 3, 4, 4, 5, 3, 2, 1};
        for (int n : numbers) {
            set.add(n);
        }
    }
    @Test
    public void checkContainsElement() {
        assertTrue(set.contain(3));
    }

    @Test
    public void whenAddThenDoesntContainTheSame() {
        List<Integer> result = new ArrayList<>();
        for (int n : set) {
          result.add(n);
        }
        assertThat(result, is(List.of(1, 2, 3, 4, 5)));
    }
}