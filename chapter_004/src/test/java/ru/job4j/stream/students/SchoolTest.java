package ru.job4j.stream.students;

import org.junit.Test;
import org.junit.Before;

import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class SchoolTest {
    private List<Student> students;
    private School school;

    @Before
    public void init() {
        school = new School();
        students = List.of(
                new Student(15),
                new Student(20),
                new Student(30),
                new Student(80),
                new Student(55),
                new Student(75),
                new Student(40),
                new Student(88),
                new Student(100),
                new Student(12),
                new Student(60));
    }

    @Test
    public void whenGetListAThenListA() {
        List<Student> student = school.
                collect(students, x -> x.getScore() >= 70);
        assertThat(student.size(), is(4));
    }

    @Test
    public void whenGetListBThenListB() {
        List<Student> student = school.
                collect(students, x -> x.getScore() >= 50 && x.getScore() < 70);
        assertThat(student.size(), is(2));
    }

    @Test
    public void whenGetListCThenListC() {
        List<Student> student = school.
                collect(students, x -> x.getScore() < 50);
        assertThat(student.size(), is(5));
    }

    @Test
    public void whenGetListNullThenListNull() {
        List<Student> student = school.
                collect(students, x -> x.getScore() < 12);
        assertThat(student.size(), is(0));
    }
}
