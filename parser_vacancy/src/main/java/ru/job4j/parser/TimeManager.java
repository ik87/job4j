package ru.job4j.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.*;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class TimeManager {
    private static final Logger LOG = LogManager.getLogger(TimeManager.class.getName());
    private Properties properties;

    public void putConfig(Properties properties) {
        this.properties = properties;
    }

    public void start() {

        try {
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            // And start it off
            scheduler.start();
            // The bunch of jobs and set triggers which will be put in to schedule
            Map<JobDetail, Set<? extends Trigger>> triggersAndJobs = new LinkedHashMap<>();
            Config config = new Config();
            config.setProp(properties);
            for (int i = 1; config.setSite(i); i++) {
                for (int j = 1; config.isSiteState() && config.setJob(j); j++) {
                    if (config.isJobState()) {
                        config.setProperties();
                        JobDetail job = newJob(Executer.class)
                                .withIdentity(config.getJob())
                                .build();

                        job.getJobDataMap().put("config", config.clone());

                        Trigger trigger = newTrigger()
                                .withIdentity("trigger_" + config.getJob())
                                .startNow()
                                .withSchedule(cronSchedule(config.getCron()))
                                .build();
                        triggersAndJobs.put(job, Set.of(trigger));
                    }
                }
            }
            if (!triggersAndJobs.isEmpty()) {
                scheduler.scheduleJobs(triggersAndJobs, true);
            }
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }


    }

}
