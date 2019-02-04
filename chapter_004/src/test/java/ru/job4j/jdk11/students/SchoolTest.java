package ru.job4j.jdk11.students;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

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
        students.add(new Student("Mike", 15));
        //students.add(null);
        students.add(new Student("Den", 20));
        students.add(new Student("Ann", 30));
        //students.add(null);
        students.add(new Student("Tom", 80));
        students.add(new Student("Kan", 55));
        students.add(new Student("Kat", 75));
        students.add(new Student("Smith", 40));
        //students.add(null);
        students.add(new Student("Jan", 88));
        students.add(new Student("Ban", 100));
        students.add(new Student("Tad", 12));
        students.add(new Student("Hank", 60));

    }

    @Test
    public void whenGetListAThenListA() {
        List<Student> student = school.
                levelOf(students, 70);
        assertThat(student.size(), is(4));
    }
}
