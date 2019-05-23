package ru.job4j.parser.entities;

public class EntitySqlRu {

   public String name;
   public String link;
   public String date;
   public String desc;

    @Override
    public String toString() {
        return String.format("name: %s\nlink: %s\ndateToMillis: %s\ndesc %s\n", name, link, date, desc);
    }

}
