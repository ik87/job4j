package ru.job4j.patterns.strategy;


import org.junit.After;
import org.junit.Test;
import org.junit.Before;

import java.util.StringJoiner;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 */
public class PaintTest {

    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    @Before
    public void loadOutput() {
        System.out.println("method execute before");
        System.setOut(new PrintStream(out));
    }
    @After
    public void backOutput() {
        System.setOut(stdout);
        System.out.println("method execute after");
    }

    @Test
    public void whenDrawSquare() {
        String expected =
                new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("++++")
                .add("+  +")
                .add("+  +")
                .add("++++")
                .toString();
        new Paint().draw(new Square());
        assertThat(new String(out.toByteArray()), is(expected));
    }

    @Test
    public void whenDrawTriangle() {
        String expected =
                new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("   +   ")
                .add("  + +  ")
                .add(" +   + ")
                .add("+++++++")
                .toString();
        new Paint().draw(new Triangle());
        assertThat(new String(out.toByteArray()), is(expected));
    }
}
