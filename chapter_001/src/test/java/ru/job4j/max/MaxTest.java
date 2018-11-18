package ru.job4j.max;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MaxTest {
    /**
     * Test.
     *
     * @author Kosolapov Ilya (d_dexter@mail.ru)
     * @version $ID$
     * @since 0.1
     */
    @Test
    public void whenFirstLessSecond() {
        Max maximum = new Max();
        int result = maximum.max(1, 2);
        assertThat(result, is(2));
    }
}