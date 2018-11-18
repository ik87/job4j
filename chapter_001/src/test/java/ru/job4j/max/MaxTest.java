package ru.job4j.max;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * Test.
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class MaxTest {
    @Test
    public void whenFirstLessSecond() {
        Max maximum = new Max();
        int result = maximum.max(1, 2);
        assertThat(result, is(2));
    }

    @Test
    public void whenFirstMoreSecond() {
        Max maximum = new Max();
        int result = maximum.max(2, 1);
        assertThat(result, is(2));
    }

    @Test
    public void whenFirstMoreOther() {
        Max maximum = new Max();
        int result = maximum.max(3, 2, 1);
        assertThat(result, is(3));
    }

    @Test
    public void whenSecondMoreOther() {
        Max maximum = new Max();
        int result = maximum.max(1, 3, 2);
        assertThat(result, is(3));
    }

    @Test
    public void whenThirdMoreOther() {
        Max maximum = new Max();
        int result = maximum.max(2, 1, 3);
        assertThat(result, is(3));
    }
}