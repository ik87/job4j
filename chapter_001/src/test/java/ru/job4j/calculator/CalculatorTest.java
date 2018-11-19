package ru.job4j.calculator;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * Test.
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $ID$
 * @since 0.1
 */
public class CalculatorTest {

    @Test
    public void whenAddOnePlusOneThenTwo() {

        Calculator calc = new Calculator();
        calc.add(1D, 1D);
        double result = calc.getResult();
        double expect = 2D;
        assertThat(result, is(expect));
    }

    /**
     * Test.
     *
     * @author Kosolapov Ilya (d_dexter@mail.ru)
     * @version $ID$
     * @since 0.1
     */
    @Test
    public void whenSubTwoMinusOneThenOne() {

        Calculator calc = new Calculator();
        calc.sub(2D, 1D);
        double result = calc.getResult();
        double expect = 1D;
        assertThat(result, is(expect));
    }

    /**
     * Test.
     *
     * @author Kosolapov Ilya (d_dexter@mail.ru)
     * @version $ID$
     * @since 0.1
     */
    @Test
    public void whenMultTwoByThreeThenSix() {

        Calculator calc = new Calculator();
        calc.mult(2D, 3D);
        double result = calc.getResult();
        double expect = 6D;
        assertThat(result, is(expect));
    }

    /**
     * Test.
     *
     * @author Kosolapov Ilya (d_dexter@mail.ru)
     * @version $ID$
     * @since 0.1
     */
    @Test
    public void whenDivSixByTwoThenThree() {

        Calculator calc = new Calculator();
        calc.div(6D, 2D);
        double result = calc.getResult();
        double expect = 3D;
        assertThat(result, is(expect));
    }
}