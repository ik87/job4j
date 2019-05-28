package ru.job4j.parser.entities;

import ru.job4j.parser.Utils;

public class EntitySqlRu {
Utils utils = new Utils();
   public String name;
   public String link;
   public Long date;
   public String desc;

    @Override
    public String toString() {
        return String.format("name: %s\nlink: %s\ndateToMillis: %s\ndesc %s\n", name, link,  date, desc);
    }

}
