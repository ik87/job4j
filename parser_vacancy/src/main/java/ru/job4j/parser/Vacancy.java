package ru.job4j.parser;

import java.util.Objects;

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
