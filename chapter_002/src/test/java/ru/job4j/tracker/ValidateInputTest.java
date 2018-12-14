package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class ValidateInputTest {
    private final ByteArrayOutputStream mem = new ByteArrayOutputStream();
    private final PrintStream out = System.out;

    @Before
    public void loadMem() {
        System.setOut(new PrintStream(this.mem));
    }

    @After
    public void loadSys() {
        System.setOut(new PrintStream(this.out));
    }

    @Test
    public void whenInvalidInput() {
        ValidateInput input =
                new ValidateInput(
                        new StubInput("invalid", "1")
                );
        input.ask("Enter", new int[]{1});
        assertThat(this.mem.toString(), is(
                String.format("Please enter correct value%n")
        ));

    }

}
