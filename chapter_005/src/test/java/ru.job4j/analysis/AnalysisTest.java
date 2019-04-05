package ru.job4j.analysis;

import org.junit.Test;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static ru.job4j.analysis.Analysis.*;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 0.1
 */
public class AnalysisTest {


    @Test
    public void whenChangedThenSame() {
        Analysis analysis = new Analysis();
        List<User> previous = List.of(
                new User(1, "Jon"),
                new User(2, "Mark"),
                new User(3, "Tim"),
                new User(4, "Tom"),
                new User(5, "Pol"),
                new User(6, "Tod"),
                new User(7, "Tod")
        );
        List<User> currently = List.of(
                new User(1, "Jon"),
                new User(2, "Jane"),
                new User(4, "Tom"),
                new User(5, "Sam"),
                new User(8, "Kan"),
                new User(9, "Mike")
        );

        Info result = analysis.diff(previous, currently);
        assertThat(result.added, is(2));
        assertThat(result.changed, is(2));
        assertThat(result.deleted, is(3));


    }

}