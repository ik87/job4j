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
import java.util.Properties;

public class Executer implements Job {
    private static final Logger LOG = LogManager.getLogger(Executer.class.getName());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        Date previousFireTime = jobExecutionContext.getPreviousFireTime();
        //get filters and condition
        Parser parser = (Parser) dataMap.get("parser");
        StorageDB storageDB = (StorageDB) dataMap.get("storageDB");
        ConnectDB connectDB = (ConnectDB) dataMap.get("connectDb");

        LOG.debug("set config is complete");

        if (previousFireTime != null) {
            parser.setCondition(previousFireTime.getTime());
        }


        //get entities from parser
        List entities = parser.getEntity();

        LOG.debug("Parsed: ");
        LOG.debug(entities);
        //put to db
        /**
         * put parsed dateToMillis to DB
         */

        if (entities != null) {
            try (Connection connection = connectDB.getInstance()) {
                storageDB.setConnection(connection);
                storageDB.add(entities);
            } catch (Exception e) {
                throw new JobExecutionException(e.getMessage(), e);
            }
        }
    }

}
