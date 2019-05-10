package ru.job4j.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class ParserTable {
    private Elements tr;
    private int index;

    public void table(Document doc) {
        //return doc.getElementsByClass("postslisttopic").parents();
        tr = doc.getElementsByClass("forumTable")
                .get(0).getElementsByTag("tr");
        index = 4;
    }

    public Vacancy nextVacancy() {
        Vacancy vacancy = null;
        while (index < tr.size() && vacancy == null) {
            vacancy = trToVacancy(tr.get(index++));
        }
        return vacancy;
    }

    private Vacancy trToVacancy(Element element) {
        Vacancy vacancy = new Vacancy();
        var td = element.getElementsByTag("td");
        var title = td.get(1).children();
        vacancy.date = td.get(5).text();
        vacancy.name = title.get(0).text();
        vacancy.link = title.get(0).attr("href");
        return vacancy;
    }

    public String pageToString(Document doc) throws IOException {
        Elements elements = doc.getElementsByClass("msgBody");
        return elements.get(1).text();
    }
}
