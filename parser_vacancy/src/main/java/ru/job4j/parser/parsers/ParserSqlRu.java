package ru.job4j.parser.parsers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.parser.utils.UtilsSqlRu;
import ru.job4j.parser.Parser;
import ru.job4j.parser.entities.EntitySqlRu;

import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserSqlRu extends Parser<EntitySqlRu> {
    private final static Logger LOG = LogManager.getLogger(ParserSqlRu.class.getName());
    private UtilsSqlRu utilsSqlRu = new UtilsSqlRu();
    private String filter;
    private Long condition;

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void setCondition(Long condition) {
        this.condition = condition;
    }

    @Override
    protected EntitySqlRu row(Element element) {
        EntitySqlRu vacancy = new EntitySqlRu();
        var td = element.getElementsByTag("td");
        var title = td.get(1).children();
        vacancy.date = td.get(5).text();
        vacancy.name = title.get(0).text();
        vacancy.link = title.get(0).attr("href");
        return vacancy;
    }

    @Override
    protected Elements table(Integer index) {
        Document doc;
        try {
            doc = Jsoup.connect("https://www.sql.ru/forum/job/" + (index + 1)).get();
            Elements tr = doc.getElementsByClass("forumTable")
                    .get(0).getElementsByTag("tr");
            return new Elements(tr.subList(4, tr.size()));
        } catch (IOException | IndexOutOfBoundsException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void page(EntitySqlRu entitySqlRu) {
        Document doc;
        try {
            doc = Jsoup.connect(entitySqlRu.link).get();
            Elements elements = doc.getElementsByClass("msgBody");
            entitySqlRu.desc = elements.get(1).text();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    protected boolean filter(EntitySqlRu entitySqlRu) {
        //Pattern pattern = Pattern.compile("(?!java\\W*script)(java)",
        //      Pattern.CASE_INSENSITIVE);
        Pattern pattern = Pattern.compile(filter,
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(entitySqlRu.name);
        return matcher.find();
    }

    @Override
    protected boolean condition(EntitySqlRu entitySqlRu) {
        try {
            long date = utilsSqlRu.date(entitySqlRu.date);
            //return dateToMillis <= utilsSqlRu.dateToMillis("1 янв 19, 00:00");
            return date <= condition;
        } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
        }
        return false;
    }
}

