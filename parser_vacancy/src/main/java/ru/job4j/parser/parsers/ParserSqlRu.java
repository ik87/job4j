package ru.job4j.parser.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.parser.utils.UtilsSqlRu;
import ru.job4j.parser.Parser;
import ru.job4j.parser.entities.EntitySqlRu;

import java.io.IOException;

public class ParserSqlRu extends Parser<EntitySqlRu> {


    private UtilsSqlRu utilsSqlRu = new UtilsSqlRu();


    @Override
    protected EntitySqlRu row(Element element) {
        EntitySqlRu vacancy = new EntitySqlRu();
        var td = element.getElementsByTag("td");
        var title = td.get(1).children();
        vacancy.date = utilsSqlRu.dateToMillisRus(td.get(5).text(), config.getTimeZone());
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

    @Override
    protected boolean filterTable(EntitySqlRu entitySqlRu) {
        return matchFilter(entitySqlRu.name, config.getFilterTable());
    }

    @Override
    protected boolean filterPage(EntitySqlRu entitySqlRu) {
        return matchFilter(entitySqlRu.desc, config.getFilterPage());
    }

    @Override
    protected boolean condition(EntitySqlRu entitySqlRu) {
        return conditionState(entitySqlRu.date);
    }
}

