package ru.job4j.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * Main class for parse table and inner page
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class Parser {
    /**
     * tr elements
     */
    private Elements tr;
    /**
     * starting index tr
     */
    private int index;

    public void table(Document doc) {
        tr = doc.getElementsByClass("forumTable")
                .get(0).getElementsByTag("tr");
        index = 4;
    }

    /**
     * get vacancy from parsed tr that not  null
     * @return vacancy
     */
    public Vacancy nextVacancy() {
        Vacancy vacancy = null;
        while (index < tr.size() && vacancy == null) {
            vacancy = trToVacancy(tr.get(index++));
        }
        return vacancy;
    }

    /**
     * Parse tr and get vacancy
     * @param element
     * @return
     */
    private Vacancy trToVacancy(Element element) {
        Vacancy vacancy = new Vacancy();
        var td = element.getElementsByTag("td");
        var title = td.get(1).children();
        vacancy.date = td.get(5).text();
        vacancy.name = title.get(0).text();
        vacancy.link = title.get(0).attr("href");
        return vacancy;
    }

    /**
     * Parse inner page and get String
     * @param doc inner page
     * @return string
     */
    public String pageToString(Document doc) {
        Elements elements = doc.getElementsByClass("msgBody");
        return elements.get(1).text();
    }
}
