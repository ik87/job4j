package ru.job4j.parser.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.parser.Parser;
import ru.job4j.parser.Utils;
import ru.job4j.parser.entities.EntityAvitoRu;
import ru.job4j.parser.utils.UtilsAvitoRu;

import java.io.IOException;

public class ParserAvitoRu extends Parser<EntityAvitoRu> {
    UtilsAvitoRu utilsAvitoRu = new UtilsAvitoRu();

    @Override
    protected EntityAvitoRu row(Element element) {
        EntityAvitoRu entity = new EntityAvitoRu();
        entity.link = element.select("h3 > a").attr("abs:href");
        entity.name = element.select("h3").text();
        entity.price = element.select("div > span.price").text();
        String date = element.parent().select("div.data > div").attr("data-absolute-date").trim();
        entity.date = utilsAvitoRu.dateToMillisRus(date,config.getTimeZone());
        return entity;
    }

    @Override
    protected Elements table(Integer index) throws IOException {
        Document doc = connectToTable(index + 1);
        Elements elements =
                doc.select("div.item_table-wrapper > " +
                        "div.description.item_table-description > div.item_table-header");
        return elements;
    }

    @Override
    protected Document connectToTable(Integer index) throws IOException {

        return Jsoup.connect("https://www.avito.ru/moskva?p="+index+"&s=104&q=Ipad+2018").get();
    }

    @Override
    protected void page(EntityAvitoRu entity) throws IOException {
        Document doc = connectToPage(entity.link);
        entity.desc = doc.select("div.item-view-main.js-item-view-main > " +
                "div:nth-child(3) > div > div").html();
    }

    @Override
    protected Document connectToPage(String url) throws IOException {
        return Jsoup.connect(url).get();
    }
}
