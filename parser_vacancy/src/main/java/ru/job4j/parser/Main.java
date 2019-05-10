package ru.job4j.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        final String url = "https://www.sql.ru/forum/job";
        ConvertDate convertDate = new ConvertDate();
        Map<String, Vacancy> vacancyMap = new LinkedHashMap<>();
        Parser parser = new Parser();
        Properties config = new Properties();
        Connection connection;
        StroreSQL stroreSQL;
        Filter filter;

        //try get property file from args
        try (FileInputStream fin = new FileInputStream(args[1])) {
            config.load(fin);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }


        //try get connection to db
        try {
            Class.forName(config.getProperty("jdbc.driver"));
            connection = DriverManager.getConnection(
                    config.getProperty("jdbc.url"),
                    config.getProperty("jdbc.username"),
                    config.getProperty("jdbc.password")
            );

            filter = new Filter(config);
            stroreSQL = new StroreSQL(connection);

            //get last updated
            Long lastUpdate = stroreSQL.lastUpdate();
            if (lastUpdate.equals(-1L)) {
                lastUpdate = convertDate.convert(config.getProperty("jdbc.date"));
            }

            //set connect
            parser.setDoc(Jsoup.connect(url).get());

            //set filter
            parser.setFilter(filter);

            //parse site to vacancyMap
            int i = 0;
            boolean isEnd = false;
            do {
                //parse inner page some vacancy
                parser.setDoc(Jsoup.connect(url + "/" + i).get());
                //parse table
                for (Vacancy vacancy : parser) {
                    if (convertDate.convert(vacancy.date) > lastUpdate) {
                        Document page = Jsoup.connect(vacancy.link).get();
                        vacancy.description = parser.page(page);
                        vacancyMap.put(vacancy.name, vacancy);
                    } else {
                        isEnd = false;
                        break;
                    }

                }
                i++;

            } while (isEnd);

            //put parsed date to DB
            stroreSQL.add(vacancyMap.values());


            // } catch (SQLException | ParseException | IOException e) {
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }


    }

}
