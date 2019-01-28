
package ru.job4j.functionalinterface;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class DiapasonTest {
    /**
     * logarithm with chosen base
     *
     * @param x    logarithm
     * @param base base
     * @return result
     */
    static double log(double x, int base) {
        return Math.log(x) / Math.log(base);
    }

    /**
     * Round double
     *
     * @param val   long double
     * @param range number digit after comma
     * @return round double
     */

    static double round(double val, double range) {
        return Math.floor(val * Math.pow(10, range)) / Math.pow(10, range);
    }


    @Test
    public void whenLinearFunctionThenLinearResults() {
        Diapason function = new Diapason();
        List<Double> result = function.diapason(2, 7, x -> 2 * x + 1);
        List<Double> expected = Arrays.asList(5.0D, 7.0D, 9.0D, 11.0D, 13.0D);
        assertThat(result, is(expected));
    }

    @Test
    public void whenQuadraticFunctionThenQuadraticResults() {
        Diapason function = new Diapason();
        List<Double> result = function.diapason(5, 8, x -> Math.pow(x, 2) - x - 2);
        List<Double> expected = Arrays.asList(18D, 28D, 40D);
        assertThat(result, is(expected));
    }

    @Test
    public void whenLogarithmicFunctionThenQuadraticResults() {
        Diapason function = new Diapason();
        List<Double> result = function.diapason(5, 8, x -> round(log(x, 2), 2));
        List<Double> expected = Arrays.asList(2.32D, 2.58D, 2.8D);
        assertThat(result, is(expected));
    }


}
