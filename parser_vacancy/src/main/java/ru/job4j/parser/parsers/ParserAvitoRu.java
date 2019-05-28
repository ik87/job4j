package ru.job4j.parser.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.parser.Parser;
import ru.job4j.parser.entities.EntityAvitoRu;

import java.io.IOException;

public class ParserAvitoRu extends Parser<EntityAvitoRu> {
    @Override
    protected EntityAvitoRu row(Element element) {
        return null;
    }

    @Override
    protected Elements table(Integer index) throws IOException {
        return null;
    }

    @Override
    protected Document connectToTable(Integer index) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("https://www.avito.ru/")
                .append("moskva?q=")
                .append(config.getSubUrl()) //query like "Ipad+2018"
                .append("&p=")
                .append(index) //table
                .append("&s=104"); // sort by price
        return Jsoup.connect(stringBuilder.toString()).get();
    }

    @Override
    protected void page(EntityAvitoRu entity) throws IOException {

    }

    @Override
    protected Document connectToPage(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    @Override
    protected boolean filterTable(EntityAvitoRu entity) {
        return matchFilter(entity.name, config.getFilterTable());
    }

    @Override
    protected boolean filterPage(EntityAvitoRu entity) {
        return matchFilter(entity.desc, config.getFilterPage());
    }

    @Override
    protected boolean condition(EntityAvitoRu entity) {
        return conditionState(entity.date);
    }
}
