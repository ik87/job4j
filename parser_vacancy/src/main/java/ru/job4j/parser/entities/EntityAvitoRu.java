package ru.job4j.parser.entities;

import ru.job4j.parser.Entity;

public class EntityAvitoRu implements Entity{

    public String name;
    public String link;
    public Long date;
    public String desc;
    public String price;

    @Override
    public String getTextRow() {
        return name;
    }

    @Override
    public String getTextPage() {
        return desc;
    }

    @Override
    public Long getDate() {
        return date;
    }
    @Override
    public String toString() {
        return String.format("name: %s\nlink: %s\ndateToMillis: %s\ndesc %s\n", name, link, date, desc);
    }
}
