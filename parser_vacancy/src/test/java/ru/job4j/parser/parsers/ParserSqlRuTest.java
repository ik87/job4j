package ru.job4j.parser.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import ru.job4j.parser.Utils;
import ru.job4j.parser.entities.EntitySqlRu;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ParserSqlRuTest {

    //  private ParserSqlRu instance;

    private Document parseHtmlFile(String file) throws IOException {
        String path = getClass().getResource(file).getPath();
        File input = new File(path);
        return Jsoup.parse(input, "windows-1251");
    }

    @Test
    public void parseTableThenGetRightResult() {


        ParserSqlRu parserSqlRu = new ParserSqlRu() {
            @Override
            protected Document connectToPage(String url) throws IOException {
                return parseHtmlFile("/sql_ru/page.html");
            }

            @Override
            protected Document connectToTable(Integer index) throws IOException {
                return parseHtmlFile("/sql_ru/table.html");
            }
        };

        parserSqlRu.setFilter("(?!java\\W*script)(java)");
        parserSqlRu.setCondition(new Utils().dateToMillis("01 01 19, 00:00", Utils.EUROPE_MOSCOW));

        List<EntitySqlRu> result = parserSqlRu.getEntity();

        assertThat(result.get(0).name, is("Требуется java разработчик"));
        assertThat(result.get(0).date, is("05 мая 19, 15:01"));
        assertThat(result.get(0).link, is("page.html"));
        assertThat(result.get(0).desc, is("Требуется java разработчик junior"));

    }

}