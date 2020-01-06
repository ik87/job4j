package ru.job4j.stream.group_students;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class GroupTest {

    @Test
    public void whenAddSomeStudentsThenGroupThem() {
        List<Student> students = new ArrayList<>() {
            {
                add(new Student("Max", Set.of("football", "basketball", "arduino", "modeling")));
                add(new Student("Ted", Set.of("football", "modeling")));
                add(new Student("Stan", Set.of("football", "basketball", "modeling")));
                add(new Student("Can", Set.of("football", "arduino", "modeling")));
            }
        };

        Map<String, Set<String>> expected = new TreeMap<>() {
            {
                put("basketball", Set.of("Max", "Stan"));
                put("modeling", Set.of("Can", "Ted", "Max", "Stan"));
                put("arduino", Set.of("Can", "Max"));
                put("football", Set.of("Can", "Ted", "Max", "Stan"));
            }
        };

        var result = Group.sections(students);
        assertThat(result, is(expected));

    }

}