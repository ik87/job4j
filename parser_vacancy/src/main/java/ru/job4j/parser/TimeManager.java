package ru.job4j.parser;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.*;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Main class define Time manager that use quartz api
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class TimeManager {
    private Properties properties;
    private Class<? extends Job> execute;
    private ConnectDB connectDB;
    private Parser parser;
    private StorageDB storageDB;

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void setExecute(Class<? extends Job> execute) {
        this.execute = execute;
    }

    public void setConnectDB(ConnectDB connectDB) {
        this.connectDB = connectDB;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    public void setStorageDB(StorageDB storageDB) {
        this.storageDB = storageDB;
    }

    /**
     * Main method. After some preparing it can start job
     * @throws SchedulerException if something went wrong
     */
    public void start() throws SchedulerException {

            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            // And start it off
            scheduler.start();

            JobDetail job = newJob(execute)
                    .withIdentity("job")
                    .build();
            job.getJobDataMap().put("connectDb", connectDB);
            job.getJobDataMap().put("storageDB", storageDB);
            job.getJobDataMap().put("parser", parser);


            Trigger trigger = newTrigger()
                    .withIdentity("trigger_job")
                    .startNow()
                    .withSchedule(cronSchedule(properties.getProperty("cron.time")))
                    .build();
            scheduler.scheduleJob(job, trigger);
    }

}
