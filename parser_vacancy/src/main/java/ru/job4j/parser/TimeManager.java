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

    public TimeManager(Properties properties, Class<? extends Job> execute) {
        this.properties = properties;
        this.execute = execute;
    }

    /**
     * Main method. After some preparing it can start job
     *
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
        job.getJobDataMap().put("properties", properties);

        Trigger trigger = newTrigger()
                .withIdentity("trigger_job")
                .startNow()
                .withSchedule(cronSchedule(properties.getProperty("cron.time")))
                .build();
        scheduler.scheduleJob(job, trigger);
    }

}
