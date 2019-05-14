package ru.job4j.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

/**
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class ParserTest {

    @Ignore
    @Test
    public void getParsedData() throws Exception {
        Parser parser = new Parser();
        String url = "https://www.sql.ru/forum/job";
        var doc = Jsoup.connect(url).get();

        Converter converter = new Converter();

        Long minDate = converter.date("01 янв 19, 00:00");

        boolean flag = true;
        int i = 1;
        parser.table(doc);
        Predicate<String> filter = new Filter(config().getProperty("jdbc.filter"));
        List<Vacancy> vacancies = new ArrayList<>();

        //parse to list Vacancies
        do {
            parser.table(Jsoup.connect(url + "/" + i).get());
            Vacancy vacancy = null;
            while ((vacancy = parser.nextVacancy()) != null) {
                if (converter.date(vacancy.date) > minDate) {
                    if (filter.test(vacancy.name)) {
                        Document page = Jsoup.connect(vacancy.link).get();
                        vacancy.description = parser.pageToString(page);
                        vacancies.add(vacancy);
                        System.out.println(vacancy);
                    }
                } else {
                    flag = false;
                    break;
                }
            }
            i++;

        } while (flag);

        //parse put to sql
        try (StoreSQL trackerSQL = new StoreSQL(ConnectionRollback.create(this.init(config())))) {
            trackerSQL.add(vacancies);
            Long time = trackerSQL.lastUpdate();
            System.out.println(new Timestamp(time));
        }
    }

    @Test
    public void parseTableThenGetRightResult() throws Exception {
        String path = Parser.class.getResource("/test_files/vacancyTable.html").getPath();
        File input = new File(path);
        Parser parser = new Parser();
        parser.table(Jsoup.parse(input, "windows-1251"));
        Filter filter = new Filter(config().getProperty("jdbc.filter"));
        List<Vacancy> result = new ArrayList<>();
        Vacancy vacancy;
        while ((vacancy = parser.nextVacancy()) != null) {
            if (filter.test(vacancy.name)) {
                result.add(vacancy);
            }
        }
        assertThat(result.get(0).name, is("Требуется java разработчик"));
        assertThat(result.get(0).date, is("05 мая 19, 15:01"));
        assertThat(result.get(0).link, is("vacancyPage.html"));
    }

    @Test
    public void parsePageThenGetRightResult() throws Exception {
        String path = Parser.class.getResource("/test_files/vacancyPage.html").getPath();
        File input = new File(path);
        Parser parser = new Parser();
        String page = parser.pageToString(Jsoup.parse(input, "windows-1251"));
        assertThat(page, is("Требуется java разработчик junior"));
    }

    @Test
    public void putValueToDBThenGetLastUpdate() throws Exception {
        try (StoreSQL storeSQL = new StoreSQL(ConnectionRollback.create(init(config())))) {
            Vacancy vacancy = new Vacancy();
            vacancy.date = "05 мая 19, 15:01";
            vacancy.name = "Требуется java разработчик";
            vacancy.description = "Требуется java разработчик junior";
            vacancy.link = "vacancyPage.html";
            storeSQL.add(List.of(vacancy));
            Long result = storeSQL.lastUpdate();
            assertThat(result, not(-1L));
        }
    }

    //connect to jdbc
    public Connection init(Properties config) {
        try {
            Class.forName(config.getProperty("jdbc.driver"));
            return DriverManager.getConnection(
                    config.getProperty("jdbc.url"),
                    config.getProperty("jdbc.username"),
                    config.getProperty("jdbc.password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    //get Properties
    public Properties config() throws IOException {
        Properties config;
        try (InputStream in = Parser.class.getClassLoader().getResourceAsStream("app.properties")) {
            config = new Properties();
            config.load(in);
        }
        return config;
    }

    /**
     * Connection, which rollback all commits.
     * It is used for integration test.
     */
    public static class ConnectionRollback {

        /**
         * Create connection with autocommit=false mode and rollback call, when connection is closed.
         *
         * @param connection connection.
         * @return Connection object.
         * @throws SQLException possible exception.
         */
        public static Connection create(Connection connection) throws SQLException {
            connection.setAutoCommit(false);
            return (Connection) Proxy.newProxyInstance(
                    ConnectionRollback.class.getClassLoader(),
                    new Class[]{Connection.class},
                    (proxy, method, args) -> {
                        Object rsl = null;
                        if ("close".equals(method.getName())) {
                            //  connection.commit();
                            connection.rollback();
                            connection.close();
                        } else {
                            rsl = method.invoke(connection, args);
                        }
                        return rsl;
                    }
            );
        }
    }

}