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
    private List<Class<? extends Job>> classesJob = new ArrayList<>();
    private Properties config;

    public void putJobs(Class<? extends Job> job) {
        classesJob.add(job);
    }

    public void putConfig(Properties config) {
        this.config = config;
    }

    public void start() {

        try {
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            // And start it off
            scheduler.start();
            // The bunch of jobs and set triggers which will be put in to schedule
            Map<JobDetail, Set<? extends Trigger>> triggersAndJobs = new LinkedHashMap<>();
            for (Class classJob : classesJob) {
                String prefix = propertiesPrefix(classJob);
                String on = config.getProperty(prefix + ".state");
                String group = config.getProperty(prefix + ".cron");

                if ("on".equals(on)) {
                    JobDetail job = newJob(classJob)
                            .withIdentity(prefix, group)
                            .build();
                    job.getJobDataMap().put("properties", config);
                    LOG.debug(config.getProperty("cron." + group));
                    Trigger trigger = newTrigger()
                            .withIdentity("trigger_" + prefix, group)
                            .startNow()
                            .withSchedule(cronSchedule(config.getProperty("cron." + group)))
                            .build();
                    triggersAndJobs.put(job, Set.of(trigger));
                }
            }
            if (!triggersAndJobs.isEmpty()) {
                scheduler.scheduleJobs(triggersAndJobs, true);
            }
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }


    }

    private String propertiesPrefix(Class<?> clazz) {
        String classname = clazz.getSimpleName();
        String prefix = classname.replaceAll("^Execute([A-Z][a-z]*)([A-Z][a-z]*)$", "$1_$2");
        return prefix.toLowerCase();
    }

}
