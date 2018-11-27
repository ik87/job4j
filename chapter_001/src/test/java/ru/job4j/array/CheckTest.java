package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CheckTest {

    @Test
    public void whenOddDataMonoByTrueThenTrue() {
        Check checker = new Check();
        boolean[] input = new boolean[]{true, true, true, true};
        boolean result = checker.mono(input);
        assertThat(result, is(true));
    }

    @Test
    public void whenOddDataNotMonoByTrueThenFalse() {
        Check checker = new Check();
        boolean[] input = new boolean[]{true, false, true, false};
        boolean result = checker.mono(input);
        assertThat(result, is(false));
    }

    @Test
    public void whenOddDataMonoByFalseThenTrue() {
        Check checker = new Check();
        boolean[] input = new boolean[]{false, false, false, false};
        boolean result = checker.mono(input);
        assertThat(result, is(true));
    }
    @Test
    public void whenEvenDataMonoByTrueThenTrue() {
        Check checker = new Check();
        boolean[] input = new boolean[]{true, true, true};
        boolean result = checker.mono(input);
        assertThat(result, is(true));
    }

    @Test
    public void whenEvenEvenNotMonoByTrueThenFalse() {
        Check checker = new Check();
        boolean[] input = new boolean[]{true, false, true};
        boolean result = checker.mono(input);
        assertThat(result, is(false));
    }

    @Test
    public void whenEvenDataMonoByFalseThenTrue() {
        Check checker = new Check();
        boolean[] input = new boolean[]{false, false, false};
        boolean result = checker.mono(input);
        assertThat(result, is(true));
    }
}
