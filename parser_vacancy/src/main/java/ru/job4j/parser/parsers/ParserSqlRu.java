package ru.job4j.parser.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.parser.Utils;
import ru.job4j.parser.Parser;
import ru.job4j.parser.entities.EntitySqlRu;

import java.io.IOException;
import java.util.TimeZone;

/**
 * Defines some methods that used in Parser
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class ParserSqlRu extends Parser<EntitySqlRu> {
    private Utils utilsRu = new Utils();
    private TimeZone timeZone = TimeZone.getTimeZone("Europe/Moscow");


    @Override
    protected EntitySqlRu row(Element element) {


        EntitySqlRu vacancy = new EntitySqlRu();
        var td = element.getElementsByTag("td");
        var title = td.get(1).children();
        vacancy.date = utilsRu.dateToMillisRus(
                td.get(5).text(),
                timeZone,
                "dd MMM yy, HH:mm"
        );
        vacancy.name = title.get(0).text();
        vacancy.link = title.get(0).attr("href");
        return vacancy;
    }

    @Override
    protected Elements table(Integer index) throws IOException {
        Document doc = connectToTable(index + 1);
        Elements tr = doc.getElementsByClass("forumTable")
                .get(0).getElementsByTag("tr");
        return new Elements(tr.subList(4, tr.size()));
    }

    @Override
    protected Document connectToTable(Integer index) throws IOException {
        return Jsoup.connect("https://www.sql.ru/forum/job/" + index).get();
    }

    @Override
    protected void page(EntitySqlRu entitySqlRu) throws IOException {
        Document doc = connectToPage(entitySqlRu.link);
        Elements elements = doc.getElementsByClass("msgBody");
        entitySqlRu.desc = elements.get(1).text();

    }

    @Override
    protected Document connectToPage(String url) throws IOException {
        return Jsoup.connect(url).get();
    }
}

