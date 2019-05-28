package ru.job4j.parser.entities;

public class EntityAvitoRu {
    public String name;
    public String link;
    public Long date;
    public String desc;
    public String price;

    @Override
    public String toString() {
        return String.format("name: %s\nlink: %s\ndateToMillis: %s\ndesc %s\n", name, link, date, desc);
    }
}
