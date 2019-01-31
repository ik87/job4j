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
        students = new ArrayList<>();
        students.add(new Student(15));
        students.add(new Student(20));
        students.add(new Student(30));
        students.add(new Student(80));
        students.add(new Student(55));
        students.add(new Student(75));
        students.add(new Student(40));
        students.add(new Student(88));
        students.add(new Student(100));
        students.add(new Student(12));
        students.add(new Student(60));

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
