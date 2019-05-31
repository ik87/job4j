package ru.job4j.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.SchedulerException;
import ru.job4j.parser.parsers.ParserSqlRu;
import ru.job4j.parser.queries.QuerySqlRu;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.TimeZone;

/**
 * Main class, entry point for parsing data
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class Main {


    private static final Logger LOG = LogManager.getLogger(Main.class.getName());
    /**
     * if debug true then properties get from resources
     * else false then file properties get from args[0]
     */
    private static final boolean DEBUG = true;
    // private static TimeManager timeManager = new TimeManager();
    private static TimeManager timeManager = new TimeManager();

    public static void main(String[] args) {
        Utils utils = new Utils();
        Properties config = new Properties();
        if (DEBUG) {
            //get properties from resources\app.properties
            try {
                config = utils.config();
                LOG.info("load property...");
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
                System.exit(1);
            }
        } else {
            //get property file from args
            try (FileInputStream fin = new FileInputStream(args[0])) {
                config.load(fin);
                LOG.info("load property...");
            } catch (Exception e) {
                LOG.error("file with properties not found");
                System.exit(1);
            }
        }

        TimeZone timeZone = TimeZone.getTimeZone("Europe/Moscow");
        String filter = "(?!java\\W*script)(java)";
        Long condition = utils.dateToMillis("01 01 19, 00:00", timeZone, "dd MM yy, HH:mm");
        ConnectDB connectDB = () -> StorageDB.init(utils.config());

        Parser parserSqlRu = new ParserSqlRu();
        parserSqlRu.setCondition(condition);
        parserSqlRu.setFilter(filter);

        timeManager.setProperties(config);
        timeManager.setExecute(Executer.class);
        timeManager.setConnectDB(connectDB);
        timeManager.setParser(parserSqlRu);
        timeManager.setStorageDB(new QuerySqlRu());

        try {
            timeManager.start();
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }


    }

}
