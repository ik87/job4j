package ru.job4j.parser.executer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import ru.job4j.parser.StorageDB;
import ru.job4j.parser.Utils;
import ru.job4j.parser.entities.EntitySqlRu;
import ru.job4j.parser.parsers.ParserSqlRu;
import ru.job4j.parser.queries.QuerySqlRu;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class ExecuteSqlRu implements Job {
    private final static Logger LOG = LogManager.getLogger(ExecuteSqlRu.class.getName());

    public ExecuteSqlRu() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        LOG.debug("Start parsed");
        /**
         * get properties settings
         */
        Properties config = (Properties) dataMap.get("properties");
        LOG.debug("get config");
        //get last update time
        Long lastUpdate = getLastTimeUpdate(config, jobExecutionContext.getPreviousFireTime());

        LOG.debug("get time " + new Utils().millisToDate(lastUpdate, Utils.EUROPE_MOSCOW));

        ParserSqlRu parserSqlRu = new ParserSqlRu();
        parserSqlRu.setCondition(lastUpdate);
        parserSqlRu.setFilter(config.getProperty("sql_ru.filter"));


        List<EntitySqlRu> entities = parserSqlRu.getEntity();
        LOG.debug("Parsed");
        LOG.debug(entities);
        /**
         * put parsed dateToMillis to DB
         */
        try {
            Connection connection = StorageDB.init(config);
            try (StorageDB storageDB = new QuerySqlRu(connection)) {
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

    public Long getLastTimeUpdate(Properties config, Date lastFireTime) throws JobExecutionException {

        String lastTimeProperty = config.getProperty("sql_ru.parse_with");
        Long lastTimeUpdate = null;
        lastTimeUpdate = new Utils().dateToMillis(lastTimeProperty, Utils.EUROPE_MOSCOW);
        if (lastFireTime != null) {
            lastTimeUpdate = lastFireTime.getTime();
        }
        return lastTimeUpdate;
    }
}
