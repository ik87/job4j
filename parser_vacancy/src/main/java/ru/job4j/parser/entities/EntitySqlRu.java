package ru.job4j.parser.entities;

import ru.job4j.parser.Entity;

public class EntitySqlRu implements Entity {

    public String name;
    public String link;
    public Long date;
    public String desc;

    @Override
    public String getTextRow() {
        return name;
    }

    @Override
    public Long getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("name: %s\nlink: %s\ndate: %s\ndesc %s\n", name, link, date, desc);
    }

}
