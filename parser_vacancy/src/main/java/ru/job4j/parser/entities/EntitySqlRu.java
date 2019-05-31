package ru.job4j.parser.entities;

import ru.job4j.parser.Entity;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
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
