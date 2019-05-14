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

/**
 * Main class, entry point for parsing data
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class SqlRuParser {
    private static final Logger LOG = LogManager.getLogger(SqlRuParser.class.getName());
    /**
     * if debug true then properties get from resources
     * else false then file properties get from args[0]
     */
    private static final boolean DEBUG = true;

    public static void main(String[] args) {

        Properties config = new Properties();
        if (DEBUG) {
            //get properties from resources\app.properties
            try {
                config = config();
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
            // send properties config to MainJobSQLru.class
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


    /**
     * Get properties from resources  and decoding cyrillic
     *
     * @return Properties
     * @throws IOException if file not found
     */
    private static Properties config() throws IOException {
        Properties config;
        try (InputStream in = Parser.class.getClassLoader().getResourceAsStream("app.properties");
             InputStreamReader inEnc = new InputStreamReader(in, "UTF-8");) {
            config = new Properties();
            config.load(inEnc);
        }
        return config;
    }
}
