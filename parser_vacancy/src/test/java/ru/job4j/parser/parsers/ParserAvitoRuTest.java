package ru.job4j.parser.parsers;

import org.apache.logging.log4j.util.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.parser.utils.UtilsRu;


import java.util.TimeZone;

public class ParserAvitoRuTest {
    @Ignore
    @Test
    public void parsePageTable() throws Exception {
        Document table = Jsoup.connect("https://www.avito.ru/moskva?p=1&s=104&q=Ipad+2018").get();
        var element =
                table.select("div.item_table-wrapper > div.description.item_table-description > div.item_table-header");
        //System.out.println(element);

        String href = element.get(0).select("h3 > a").attr("abs:href");
        System.out.println(href);

        String name = element.get(0).select("h3").text();
        System.out.println(name);

        String price = element.get(0).select("div > span.price").text();
        System.out.println(price);

        String when = element.get(0).parent().select("div.data > div").attr("data-absolute-date");
        System.out.println(when);

        Document page = Jsoup.connect(href).get();

        var description = page.select("div.item-view-main.js-item-view-main > div:nth-child(3) > div > div").html();

        System.out.println(description);

    }

    @Test
    public void timeTest() {
        String template = "dd MMMM HH:mm yy";
        String format1 = "   Сегодня 10:13 ";
        String format2 = "28 мая 23:09 19";
        int resule = format2.compareTo("28 мая 23:09 19");
        System.out.println(format2.compareTo("28 мая 23:09 19"));
        // "28 мая 23:10 19"
        // "28 мая 23:09 19"
        //28 мая 23:09 19
        UtilsRu utilsRu = new UtilsRu();
        Long ltime1 = utilsRu.dateToMillisRus(
                format1.trim(), TimeZone.getTimeZone("Europe/Moscow"), template);
        System.out.println(ltime1);
        Long ltime2 = utilsRu.dateToMillisRus(
                format2, TimeZone.getTimeZone("Europe/Moscow"), template);
        System.out.println(utilsRu.millisToDate(ltime2, TimeZone.getTimeZone("Europe/Moscow")));
        System.out.println(ltime2);

    }

}