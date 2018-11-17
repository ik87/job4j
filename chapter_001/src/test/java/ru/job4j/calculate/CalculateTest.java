package ru.job4j.calculate;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CalculateTest {
    /**
     * Test.
     *
     * @author Kosolapov Ilya(d_dexter@mail.ru)
     * @version $ID$
     * @since 0.1
     */
    @Test
    public void when60RubleToDollarThen1() {
        Calculate calc = new Calculate();
        int result = calc.rubleToDollar(60);
        assertThat(result, is(1));
    }

    @Test
    public void when70RubleToEuroThen1() {
        Calculate calc = new Calculate();
        int result = calc.rubleToEuro(70);
        assertThat(result, is(1));
    }

    @Test
    public void when1DollarToRubleThen60() {
        Calculate calc = new Calculate();
        int result = calc.dollarToRuble(1);
        assertThat(result, is(60));
    }

    @Test
    public void when1EuroToRubleThen70() {
        Calculate calc = new Calculate();
        int result = calc.euroToRuble(1);
        assertThat(result, is(70));
    }
}