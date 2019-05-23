package ru.job4j.parser;

import org.junit.Ignore;
import org.junit.Test;

import ru.job4j.parser.entities.EntitySqlRu;
import ru.job4j.parser.executer.ExecuteSqlRu;
import ru.job4j.parser.parsers.ParserSqlRu;


public class ParserTest {

    @Ignore
    @Test
    public void parseSqlRu() {
        ParserSqlRu parserSqlRu = new ParserSqlRu();
        parserSqlRu.setCondition(new Utils().dateToMillis("1 01 19, 00:00", Utils.EUROPE_MOSCOW));
        parserSqlRu.setFilter("(?!java\\W*script)(java)");

        for (EntitySqlRu entitySqlRu : parserSqlRu) {
            System.out.print(entitySqlRu);
        }
    }

    @Test
    public void checkClass() {
        String classname = new ExecuteSqlRu().getClass().getSimpleName();
        String name = classname.replaceAll("^Execute([A-Z][a-z]*)([A-Z][a-z]*)$", "$1_$2").toLowerCase();
        System.out.println(name);
    }

}