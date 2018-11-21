package ru.job4j.loop;

import org.junit.Test;
import java.util.StringJoiner;
import static org.hamcrest.Matchers.is;
import  static org.junit.Assert.assertThat;

/**
 * Test
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class PaintTest {

    @Test
    public void whenPiramid4() {
        Paint paint = new Paint();
        String rst = paint.piramid(4);
        System.out.println(rst);
        assertThat(rst, is(
                new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                        .add("   ^   ")
                        .add("  ^^^  ")
                        .add(" ^^^^^ ")
                        .add("^^^^^^^")
                        .toString()
        ));
    }

    @Test
    public void whenPiramid2() {
        Paint paint = new Paint();
        String rst = paint.piramid(2);
        System.out.println(rst);
        assertThat(rst, is(
                new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                        .add(" ^ ")
                        .add("^^^")
                        .toString()
        ));
    }
}