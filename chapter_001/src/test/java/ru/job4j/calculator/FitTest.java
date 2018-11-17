package ru.job4j.calculator;
import org.junit.Test;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

public class FitTest {
    /**
     * Test.
     *
     * @author Kosolapov Ilya (d_dexter@mail.ru)
     * @version $ID$
     * @since 0.1
     */
    @Test
    public void manWight() {
        Fit fit = new Fit();
        double weight = fit.manWeight(180);
        assertThat(weight, closeTo(92.0, 0.1));
    }
    @Test
    public void womanWight() {
        Fit fit = new Fit();
        double weight = fit.womanWeight(170);
        assertThat(weight, closeTo(69.0, 0.1));
    }
}
