package ru.job4j.parser;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Executer implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        Date previousFireTime = jobExecutionContext.getPreviousFireTime();
        //get filters and condition
        Config config = (Config) dataMap.get("config");

        Class<? extends StorageDB> dbClass;
        Class<? extends Parser> parserClass;
        Parser parser;

        try {
            dbClass = (Class<? extends StorageDB>) Class.forName("ru.job4j.parser.queries.Query" + config.getSite());
            parserClass = (Class<? extends Parser>) Class.forName("ru.job4j.parser.parsers.Parser" + config.getSite());
            parser = parserClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new JobExecutionException(e.getMessage(), e);
        }


        Long condition = previousFireTime != null
                ? previousFireTime.getTime() : config.getParseWith();

        config.setParseWith(condition);

        //set config parse
        parser.setConfig(config);

        //get entities from parser
        List entities = parser.getEntity();
        System.out.println(entities);

        //put to db
        /**
         * put parsed dateToMillis to DB
         */
        try {
            Connection connection = init(config);
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


    public Connection init(Config config)
            throws ClassNotFoundException, SQLException {
        Class.forName(config.getDriver());
        return DriverManager.getConnection(config.getSubUrl(), config.getUsername(), config.getPassword());
    }
}
