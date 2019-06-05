package ru.job4j.parser.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import ru.job4j.parser.Utils;
import ru.job4j.parser.entities.EntitySqlRu;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.TimeZone;

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


        TimeZone timeZone = TimeZone.getTimeZone("Europe/Moscow");
        String filter = "(?!java\\W*script)(java)";
        Long condition = new Utils().dateToMillis("01 01 19, 00:00", timeZone, "dd MM yy, HH:mm");

        parserSqlRu.setCondition(condition);
        parserSqlRu.setFilter(filter);

        List<EntitySqlRu> result = parserSqlRu.getEntity();

        assertThat(result.get(0).getName(), is("Требуется java разработчик"));
        assertThat(result.get(0).getDate(), is(1557057660000L));
        assertThat(result.get(0).getLink(), is("page.html"));
        assertThat(result.get(0).getDesc(), is("Требуется java разработчик junior"));

    }

}