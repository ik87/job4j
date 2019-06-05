package ru.job4j.parser.executers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import ru.job4j.parser.*;
import ru.job4j.parser.entities.EntitySqlRu;
import ru.job4j.parser.parsers.ParserSqlRu;
import ru.job4j.parser.queries.QuerySqlRu;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

/**
 * Binds together.
 * Where messages passed by scheme: Parser -> List -> Storage
 * and repeats them time-to-time
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class ExecuterSqlRu implements Job {
    private static final Logger LOG = LogManager.getLogger(ExecuterSqlRu.class.getName());

    private Utils utils = new Utils();
    private final static String FILTER = "(?!java\\W*script)(java)";
    private final TimeZone timeZone = TimeZone.getTimeZone("Europe/Moscow");
    private final Long condition = utils.dateToMillis("01 01 19, 00:00", timeZone, "dd MM yy, HH:mm");


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        //get Properties
        Properties properties = (Properties) dataMap.get("properties");

        //instance objects
        Parser<EntitySqlRu> parser = new ParserSqlRu();
        StorageDB<EntitySqlRu> storageDB = new QuerySqlRu();
        ConnectDB connectDB = () -> StorageDB.init(properties);

        LOG.debug("set config is complete");

        //get previous time start, if never then null
        Date previousFireTime = jobExecutionContext.getPreviousFireTime();

        //check it
        if (previousFireTime != null) {
            parser.setCondition(previousFireTime.getTime());
        } else {
            parser.setCondition(condition);
        }

        //set parser FILTER
        parser.setFilter(FILTER);


        //get entities from parser
        List<EntitySqlRu> entities = parser.getEntity();

        LOG.debug("Parsed: ");
        LOG.debug(entities);

        //put parsed date to DB
        if (entities != null) {
            try (Connection connection = connectDB.getInstance()) {
                connection.setAutoCommit(false);
                storageDB.setConnection(connection);
                try {
                    storageDB.add(entities);
                    connection.commit();
                } catch (SQLException e) {
                    connection.rollback();
                    throw e;
                }
            } catch (Exception e) {
                throw new JobExecutionException(e.getMessage(), e);
            }
        }
    }

}