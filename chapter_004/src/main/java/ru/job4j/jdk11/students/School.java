package ru.job4j.jdk11.students;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @since $ID$
 * @version 0.1
 */
public class School {
    public List<Student> levelOf(List<Student> students, final int bound) {
        return students
                .stream()
                .sorted(Comparator.comparing(Student::getScore).reversed())
                .flatMap(Stream::ofNullable)
                .takeWhile(x->x.getScore() > bound)
                .collect(Collectors.toList());
    }
}
