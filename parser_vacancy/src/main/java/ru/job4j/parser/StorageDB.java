package ru.job4j.parser;

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

    Connection connection;

    protected abstract String sqlQuery();

    protected abstract void putEntities(PreparedStatement pstmt, T entity) throws SQLException;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Add entity to table
     *
     * @param entities Collection parsed entities
     */
    public void add(Collection<T> entities) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(sqlQuery());
        for (T entity : entities) {
            putEntities(pstmt, entity);
            pstmt.addBatch();
        }
        pstmt.executeBatch();
    }


    public static Connection init(Properties config)
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
