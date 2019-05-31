package ru.job4j.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Binds together.
 * Where messages passed by scheme: Parser -> List<Entity> -> Storage
 * and repeats them time-to-time
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class Executer implements Job {
    private static final Logger LOG = LogManager.getLogger(Executer.class.getName());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        //get instance objects
        Parser parser = (Parser) dataMap.get("parser");
        StorageDB storageDB = (StorageDB) dataMap.get("storageDB");
        ConnectDB connectDB = (ConnectDB) dataMap.get("connectDb");

        LOG.debug("set config is complete");
        //get previous time start, if never then null
        Date previousFireTime = jobExecutionContext.getPreviousFireTime();
        //check it
        if (previousFireTime != null) {
            parser.setCondition(previousFireTime.getTime());
        }


        //get entities from parser
        List entities = parser.getEntity();

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
