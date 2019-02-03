package ru.job4j.stream.students_list_to_map;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class Student {
    private int score;
    private String name;

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public Student(String name, int score) {
        this.score = score;
        this.name = name;
    }
}
