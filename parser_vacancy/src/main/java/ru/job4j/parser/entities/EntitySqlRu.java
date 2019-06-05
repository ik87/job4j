package ru.job4j.parser.entities;

import ru.job4j.parser.Entity;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class EntitySqlRu implements Entity {

    private String name;
    private String link;
    private Long date;
    private String desc;

    @Override
    public String getTextRow() {
        return getName();
    }

    @Override
    public Long getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("name: %s\nlink: %s\ndate: %s\ndesc %s\n", getName(), getLink(), getDate(), getDesc());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
