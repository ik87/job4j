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
        String filterTable = dataMap.getString("filter_table");
        String filterPage = dataMap.getString("filter_page");
        Long condition = previousFireTime != null
                ? previousFireTime.getTime() : dataMap.getLong("parse_with");
        //get settings database String driver, String url, String username, String password
        String driver = dataMap.getString("jdbc_driver");
        String url = dataMap.getString("jdbc_url");
        String username = dataMap.getString("jdbc_username");
        String password = dataMap.getString("jdbc_password");

        Class<? extends StorageDB> db = (Class<? extends StorageDB>) dataMap.get("db");
        Parser parser = (Parser) dataMap.get("parser");


        //set parse
        parser.setFilterTable(filterTable);
        parser.setFilterPage(filterPage);
        parser.setCondition(condition);

        //get entities from parser
        List entities = parser.getEntity();
        System.out.println(entities);

        //put to db
        /**
         * put parsed dateToMillis to DB
         */
        try {
            Connection connection = init(driver, url, username, password);
            try (StorageDB storageDB = db.getConstructor(Connection.class).newInstance(connection)) {
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


    public Connection init(String driver, String url, String username, String password)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }
}
