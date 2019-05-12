package ru.job4j.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;


public class SqlRuParser {
    private static final Logger LOG = LogManager.getLogger(SqlRuParser.class.getName());

    public static void main(String[] args) {

        Properties config = new Properties();

        try {
            config = config();
            LOG.info("load property...");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            System.exit(1);
        }


/*
        //try get property file from args
        try (FileInputStream fin = new FileInputStream(args[0])) {
            config.load(fin);
            LOG.info("load property...");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            System.exit(1);
        }
*/
        //get last updated
        try {
            LOG.info("parser is waiting");
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
            scheduler.start();
            // define the job and tie it to our DumbJob class
            JobDetail job = newJob(MainJobSQLru.class)
                    .withIdentity("job1", "group1")
                    .build();
            job.getJobDataMap().put("properties", config);

            Trigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(cronSchedule(config.getProperty("cron.time")))
                    .build();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }



    }


    //get Properties
    public static Properties config() throws IOException {
        Properties config;
        try (InputStream in = Parser.class.getClassLoader().getResourceAsStream("app.properties");
             InputStreamReader inEnc = new InputStreamReader(in, "UTF-8");) {
            config = new Properties();
            config.load(inEnc);
        }
        return config;
    }
}
