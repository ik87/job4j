package ru.job4j.calculate;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CalculateTest {
    /**
     * Test.
     *
     * @author Petr Arsentev (parsentev@yandex.ru)
     * @version $ID$
     * @since 0.1
     */
    @Test
    public void whenAddOnePlusOneThenTwo() {

        Calculate calc = new Calculate();
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

        Calculate calc = new Calculate();
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

        Calculate calc = new Calculate();
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

        Calculate calc = new Calculate();
        calc.div(6D, 2D);
        double result = calc.getResult();
        double expect = 3D;
        assertThat(result, is(expect));
    }
}