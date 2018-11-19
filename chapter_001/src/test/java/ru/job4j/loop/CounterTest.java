package ru.job4j.loop;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class CounterTest {
    @Test
    public void whenFirstOneSecondTenThenThirty() {
        Counter count = new Counter();
        int result = count.add(1, 10);
        int expect = 30;
        assertThat(result,  is(expect));
    }
}