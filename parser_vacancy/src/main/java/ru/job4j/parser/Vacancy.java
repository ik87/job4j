package ru.job4j.parser;

/**
 * Vacancy class
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class Vacancy {
    String name;
    String description;
    String link;
    String date;

    @Override
    public String toString() {
        return String.format("name: %s\nlink: %s\ndate: %s\ndescription: %s\n", name, link, date, description);
    }
}
