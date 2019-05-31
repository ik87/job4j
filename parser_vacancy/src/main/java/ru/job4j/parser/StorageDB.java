package ru.job4j.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collection;
import java.util.Properties;

/**
 * Main class for work with DB, save vacancy, get last dateToMillis update
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public abstract class StorageDB<T> {
    private static final Logger LOG = LogManager.getLogger(StorageDB.class.getName());

    Connection connection;

    protected abstract String sqlQuery();

    protected abstract void putEntities(PreparedStatement pstmt, T entity) throws Exception;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Add entity to table
     *
     * @param entities Collection parsed entities
     */
    public void add(Collection<T> entities) {
        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery())) {
            for (T entity : entities) {
                putEntities(pstmt, entity);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (Exception e) {
            try {
                if (!connection.getAutoCommit()) {
                    connection.rollback();
                }
            } catch (Exception d) {
                LOG.error(e.getMessage(), e);
            }
            LOG.error(e.getMessage(), e);
        }
    }


    public final static Connection init(Properties config)
            throws ClassNotFoundException, SQLException {
        Class.forName(config.getProperty("jdbc.driver"));
        return DriverManager
                .getConnection(
                        config.getProperty("jdbc.url"),
                        config.getProperty("jdbc.username"),
                        config.getProperty("jdbc.password")
                );
    }
}
