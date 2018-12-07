package ru.job4j.patterns.strategy;


import org.junit.Test;

import java.util.StringJoiner;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 */
public class ShapeTest {
    @Test
    public void whenDrawSquare() {
        PrintStream stdout = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        String expected =
                new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("++++")
                .add("+  +")
                .add("+  +")
                .add("++++")
                .toString();
        new Paint().draw(new Square());
        assertThat(new String(out.toByteArray()), is(expected));
        System.setOut(stdout);
    }

    @Test
    public void whenDrawTriangle() {
        PrintStream stdout = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        String expected =
                new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("   +   ")
                .add("  + +  ")
                .add(" +   + ")
                .add("+++++++")
                .toString();
        new Paint().draw(new Triangle());
        assertThat(new String(out.toByteArray()), is(expected));
        System.setOut(stdout);
    }
}
