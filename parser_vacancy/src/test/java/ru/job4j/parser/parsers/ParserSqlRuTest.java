package ru.job4j.parser.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import ru.job4j.parser.Config;
import ru.job4j.parser.Utils;
import ru.job4j.parser.entities.EntitySqlRu;
import ru.job4j.parser.utils.UtilsRu;

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
        Config config = new Config();
        config.setFilterTable("(?!java\\W*script)(java)");
        config.setParseWith(new Utils().dateToMillis("01 01 19, 00:00", timeZone, "dd MM yy, HH:mm"));
        config.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));

        parserSqlRu.setConfig(config);
        List<EntitySqlRu> result = parserSqlRu.getEntity();

        assertThat(result.get(0).name, is("Требуется java разработчик"));
        assertThat(result.get(0).date,
                is(new UtilsRu().dateToMillisRus("05 мая 19, 15:01", timeZone, "dd MM yy, HH:mm")));
        assertThat(result.get(0).link, is("page.html"));
        assertThat(result.get(0).desc, is("Требуется java разработчик junior"));

    }

}