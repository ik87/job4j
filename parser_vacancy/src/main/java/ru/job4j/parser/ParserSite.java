package ru.job4j.parser;

import org.jsoup.nodes.Document;

import java.text.ParseException;
import java.util.*;


public class ParserSite {
    private Set<Vacancy> vacancies = new HashSet<>();
    private ConvertDate convertDate = new ConvertDate();
    //private String generalUrl = "https://www.sql.ru/forum/job/";
    private Document doc;//Document doc = Jsoup.connect("https://www.sql.ru/forum/job/1").get();
    private Long minDate; //= "01 янв 19, 00:00";
    private String[] keyWord; // = {"java", "javaEE", "javaSE"};
    private String[] keyWordExcept;// = {"javaScript", "java-Script"};
    private Long lastDate = 0L;

    public ParserSite(String[] keyWord, String[] keyWordExcept) {
        this.keyWord = keyWord;
        this.keyWordExcept = keyWordExcept;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public Long getMinDate() {
        return minDate;
    }

    public void setMinDate(Long minDate) {
        this.minDate = minDate;
    }

    private void parserTable() throws ParseException {
        var elements = doc.getElementsByClass("forumTable").get(0).getElementsByTag("tr");
        for (var tr : elements.subList(1, elements.size())) {
            var td = tr.getElementsByTag("td");
            var title = td.get(1).children();
            var link = title.get(0).attr("href");
            var name = title.get(0).text();
            var date = convertDate.convert(td.get(5).text());
            if (!title.hasClass("closedTopic") &&
                    confirm(name, keyWord) && !confirm(name, keyWordExcept) &&
                    minDate < date
            ) {
                Vacancy vacancy = new Vacancy();
                vacancy.setName(name);
                vacancy.setLink(link);
                vacancy.setDate(date);
                vacancy.setDescription(parserInnerPage(link));
                vacancies.add(vacancy);
                lastDate = date;
            }

        }
    }

    public boolean isEnd() {
        return lastDate < minDate;
    }

    private String parserInnerPage(String url) {
        return null;
    }

    private boolean confirm(String name, String[] key) {
        boolean flag = false;
        for (int i = 0; i < key.length; i++) {
            if(name.toLowerCase().contains(keyWord[i].toLowerCase())) {
                flag = true;
                break;
            }
        }
        return flag;
    }

}
