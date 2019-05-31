package ru.job4j.parser;

import java.sql.*;
import java.util.Collection;
import java.util.Properties;

/**
 * Main class for work with Database, this abstract class perform general logic,
 * child classes must overload some methods
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public abstract class StorageDB<T> {

    Connection connection;

    /**
     * Define sql query kind of
     * "INSERT INTO table (name, description, link, updated) VALUES (?, ?, ?, ?)"
     * @return String query
     */
    protected abstract String sqlInsertQuery();

    /**
     * This method needs to link members entity and Prepared Statement together
     * @param pstmt
     * @param entity
     * @throws SQLException
     */
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
        PreparedStatement pstmt = connection.prepareStatement(sqlInsertQuery());
        for (T entity : entities) {
            putEntities(pstmt, entity);
            pstmt.addBatch();
        }
        pstmt.executeBatch();
    }

    /**
     * Use for making connect
     * @param config config, it could be Utils::config method
     * @return connect
     * @throws ClassNotFoundException can throws
     * @throws SQLException  can throws
     */
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
