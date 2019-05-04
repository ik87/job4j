package ru.job4j.parser;

import org.jsoup.Jsoup;
import org.junit.Test;


public class ParserTest {

    @Test
    public void getRightDate() throws Exception {
        Parser parser = new Parser();
        String url = "https://www.sql.ru/forum/job";
        ConvertDate convertDate = new ConvertDate();
        final Long minDate = convertDate.convert("01 янв 19, 00:00");
        var doc = Jsoup.connect(url).get();
        boolean flag = true;
        int i = 1;
        parser.setDoc(doc);
        parser.setFilter(this::filter);
        do {
            parser.setDoc(Jsoup.connect(url + "/" + i).get());
            for (Vacancy vacancy : parser) {
                if (convertDate.convert(vacancy.date) >= minDate) {
                    System.out.println(vacancy);
                } else {
                    flag = false;
                    break;
                }

            }
            i++;

        } while (flag);


    }

    private boolean filter(String s) {
        s = s.toLowerCase();
        return s.contains("java") &&
                !s.contains("java script") &&
                !s.contains("javascript");

    }

}