package ru.job4j.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class Executer implements Job {
    private static final Logger LOG = LogManager.getLogger(Executer.class.getName());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        Date previousFireTime = jobExecutionContext.getPreviousFireTime();
        //get filters and condition
        Config config = (Config) dataMap.get("config");
        LOG.debug("set config is complete");

        Class<StorageDB> dbClass;
        Class<Parser> parserClass;
        Parser parser;

        try {
            dbClass = (Class<StorageDB>) Class.forName("ru.job4j.parser.queries.Query" + config.getPrefix());
            parserClass = (Class<Parser>) Class.forName("ru.job4j.parser.parsers.Parser" + config.getPrefix());
        } catch (Exception e) {
            throw new JobExecutionException(e.getMessage(), e);
        }

        LOG.debug("set classes is complete");

        Long condition = previousFireTime != null
                ? previousFireTime.getTime() : config.getParseWith();

        config.setParseWith(condition);

        try {
            parser = parserClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new JobExecutionException(e.getMessage(), e);
        }

        //set config parse
        parser.setConfig(config);
        //get entities from parser
        List entities = parser.getEntity();
        LOG.debug("Parsed: ");
        LOG.debug(entities);
        //put to db
        /**
         * put parsed dateToMillis to DB
         */
        if(entities != null) {
            try {
                Connection connection = StorageDB.init(config);
                try (StorageDB storageDB = dbClass.getConstructor(Connection.class).newInstance(connection)) {
                    connection.setAutoCommit(false);
                    storageDB.add(entities);
                    connection.commit();
                } catch (Exception e) {
                    connection.rollback();
                    throw new Exception(e);
                }
            } catch (Exception e) {
                throw new JobExecutionException(e.getMessage(), e);
            }
        }
    }

}
