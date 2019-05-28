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
    private List<Executable> listExecuts = new ArrayList<>();
    private Utils utils = new Utils();
    private Properties config;

    public void putJobs(Executable job) {
        listExecuts.add(job);
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
            for (Executable execute : listExecuts) {

                String prefix = propertiesPrefix(execute.getClass());
                String state = config.getProperty(prefix + ".state");
                // String time = config.getProperty(prefix + ".cron");

                String jobState;
                int i = 1;
                if ("on".equals(state)) {
                    while ((jobState = config.getProperty(prefix + ".job" + i + ".state")) != null) {
                        if ("on".equals(jobState)) {
                            String doublePrefix = prefix + ".job" + i;
                            String time = config.getProperty(doublePrefix + ".cron");

                            String timeZone = config.getProperty(doublePrefix + ".time_zone");
                            Long parseWith = utils.dateToMillis(config.getProperty(doublePrefix + ".parse_with"),
                                    TimeZone.getTimeZone(timeZone));
                            String filterTable = config.getProperty(doublePrefix + ".filter_table");
                            String filterPage = config.getProperty(doublePrefix + ".filter_page");
                            String jdbcDriver = config.getProperty("jdbc.driver");
                            String jdbcUrl = config.getProperty("jdbc.url");
                            String jdbcUsername = config.getProperty("jdbc.username");
                            String jdbcPassword = config.getProperty("jdbc.password");

                            JobDetail job = newJob(Executer.class)
                                    .withIdentity(doublePrefix)
                                    .build();
                            job.getJobDataMap().put("parse_with", parseWith);
                            job.getJobDataMap().put("filter_table", filterTable);
                            job.getJobDataMap().put("filter_page", filterPage);
                            job.getJobDataMap().put("parser", execute.parser());
                            job.getJobDataMap().put("db", execute.database());


                            job.getJobDataMap().put("jdbc_driver", jdbcDriver);
                            job.getJobDataMap().put("jdbc_url", jdbcUrl);
                            job.getJobDataMap().put("jdbc_username", jdbcUsername);
                            job.getJobDataMap().put("jdbc_password", jdbcPassword);

                            Trigger trigger = newTrigger()
                                    .withIdentity("trigger_" + doublePrefix)
                                    .startNow()
                                    .withSchedule(cronSchedule(time))
                                    .build();
                            triggersAndJobs.put(job, Set.of(trigger));
                        }
                        i++;
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

    private String propertiesPrefix(Class clazz) {
        String classname = clazz.getSimpleName();
        String prefix = classname.replaceAll("^Execute([A-Z][a-z]*)([A-Z][a-z]*)$", "$1_$2");
        return prefix.toLowerCase();
    }

}
