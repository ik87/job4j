package ru.job4j.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.function.Predicate;


public class Parser implements Iterable<Vacancy>{
    /*
    private Set<Vacancy> vacancies = new HashSet<>();
    private ConvertDate convertDate = new ConvertDate();
    //private String generalUrl = "https://www.sql.ru/forum/job/";
    private Document connection;//Document connection = Jsoup.connect("https://www.sql.ru/forum/job/1").get();
    private Long minDate; //= "01 янв 19, 00:00";
    private String[] keyWord; // = {"java", "javaEE", "javaSE"};
    private String[] keyWordExcept;// = {"javaScript", "java-Script"};
    private Long lastDate = 0L;
    */
    private Document doc;
    private Predicate<String> filter;

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    @Override
    public Iterator<Vacancy> iterator() {
        return new ParserIterator();
    }

    public Elements table(Document doc) {
        return doc.getElementsByClass("postslisttopic").parents();
    }

    private Vacancy elementToVacancy(Element element) {
        Vacancy vacancy = null;
        var td = element.getElementsByTag("td");
        var title = td.get(1).children();
        var link = title.get(0).attr("href");
        var name = title.get(0).text();
        var date = td.get(5).text();
        if(filter.test(name)) {
            vacancy = new Vacancy();
            vacancy.date = date;
            vacancy.name = name;
            vacancy.link = link;
        }

        return vacancy;
    }

    private String page(Element element) {
        return null;
    }

    public void setFilter(Predicate<String> filter) {
        this.filter = filter;
    }

    private class ParserIterator implements Iterator<Vacancy> {
        private int index = 9;
        private Elements elements = table(doc);
        private Vacancy vacancy = nextVacancy();
        @Override
        public boolean hasNext() {
            return vacancy != null;
        }

        @Override
        public Vacancy next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Vacancy tmp = vacancy;
            vacancy = nextVacancy();
            return tmp;
        }

        Vacancy nextVacancy() {
            Vacancy vacancy = null;
            while (index < elements.size() && vacancy == null) {
                vacancy = elementToVacancy(elements.get(index++));
            }
            return vacancy;
        }
    }
}
