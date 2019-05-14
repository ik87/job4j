package ru.job4j.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.quartz.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class MainJobSQLru implements Job {
    private static final Logger LOG = LogManager.getLogger(MainJobSQLru.class.getName());

    private Filter filter;
    private Long lastUpdate;
    private List<Vacancy> vacancies;

    public MainJobSQLru() {
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        /**
         * get properties settings
         */
        Properties config = (Properties) dataMap.get("properties");

        /**
         * get last updated
         */
        try (StoreSQL storeSQL = new StoreSQL(init(config))) {
            lastUpdate = lastUpdate(storeSQL, config);
            LOG.info("load last update time: " + new Timestamp(lastUpdate));
        } catch (Exception e) {
            throw new JobExecutionException(e.getMessage(), e);
        }

        /**
         * parse site to vacancy
         */
        try {
            filter = new Filter(config.getProperty("jdbc.filter"));
            vacancies = parseVacancies(lastUpdate, filter);
            LOG.info("Parsing was complete...");
        } catch (Exception e) {
            throw new JobExecutionException(e.getMessage(), e);
        }

        /**
         * put parsed date to DB
         */
        try {
            Connection connection = init(config);
            try (StoreSQL storeSQL = new StoreSQL(connection)) {
                connection.setAutoCommit(false);
                storeSQL.add(vacancies);
                connection.commit();
                LOG.info("Parsing data were added to DB...");
            } catch (Exception e) {
                connection.rollback();
                throw new Exception(e);
            }
        } catch (Exception e) {
            throw new JobExecutionException(e.getMessage(), e);
        }

    }

    /**
     * Main method for parsing date from  https://www.sql.ru/forum/job
     * @param lastUpdate min parsing date
     * @param filter filter
     * @return List vacancies
     * @throws IOException if url not found
     * @throws ParseException if problems with parsing
     */
    private  List<Vacancy> parseVacancies(Long lastUpdate, Filter filter)
            throws IOException, ParseException {
        Parser parser = new Parser();
        String url = "https://www.sql.ru/forum/job";
        var doc = Jsoup.connect(url).get();
        Converter converter = new Converter();
        boolean flag = true;
        int i = 1;
        parser.table(doc);
        List<Vacancy> vacancies = new ArrayList<>();

        //parse to list Vacancies
        do {
            parser.table(Jsoup.connect(url + "/" + i).get());
            Vacancy vacancy = null;
            while ((vacancy = parser.nextVacancy()) != null) {
                if (converter.date(vacancy.date) > lastUpdate) {
                    if (filter.test(vacancy.name)) {
                        Document page = Jsoup.connect(vacancy.link).get();
                        vacancy.description = parser.pageToString(page);
                        vacancies.add(vacancy);
                    }
                } else {
                    flag = false;
                    break;
                }
            }
            i++;
        } while (flag);
        return vacancies;
    }

    private  Connection init(Properties config) throws ClassNotFoundException, SQLException {
        Class.forName(config.getProperty("jdbc.driver"));
        return DriverManager.getConnection(
                config.getProperty("jdbc.url"),
                config.getProperty("jdbc.username"),
                config.getProperty("jdbc.password")
        );
    }

    private  Long lastUpdate(StoreSQL storeSQL, Properties config) throws SQLException, ParseException {
        Converter converter = new Converter();
        Long lastUpdateProperty = converter.date(config.getProperty("jdbc.date"));
        Long lastUpdate = storeSQL.lastUpdate();
        if (lastUpdate.equals(-1L) || lastUpdateProperty > lastUpdate) {
            lastUpdate = lastUpdateProperty;
        }
        return lastUpdate;
    }

}
