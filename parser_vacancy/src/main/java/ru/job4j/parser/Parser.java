package ru.job4j.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;


public class Parser implements Iterable<Vacancy>{
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
        //return doc.getElementsByClass("postslisttopic").parents();
        return doc.getElementsByClass("forumTable")
                .get(0).getElementsByClass("postslisttopic").parents();
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

    public String page(Document doc) throws IOException {
        Elements elements = doc.getElementsByClass("msgBody");
        return elements.get(1).text();
    }

    public void setFilter(Predicate<String> filter) {
        this.filter = filter;
    }

    private class ParserIterator implements Iterator<Vacancy> {
        private int index = 0;
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
