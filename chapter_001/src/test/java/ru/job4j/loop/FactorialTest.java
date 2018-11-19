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
public class FactorialTest {
    @Test
    public void when5Then120() {
        Factorial fact = new Factorial();
        int result = fact.calc(5);
        int expect = 120;
        assertThat(result, is(expect));
    }
}