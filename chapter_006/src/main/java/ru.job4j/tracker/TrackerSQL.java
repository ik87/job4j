package ru.job4j.tracker;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Sql tracker
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class TrackerSQL implements AutoCloseable, ITracker {

    private Connection connection;
    private InputStream config = TrackerSQL.class.getClassLoader().getResourceAsStream("app.properties");

    public TrackerSQL(Connection connection) {
        this.connection = connection;
    }

    /**
     * use for track SQLException
     */
    private static final Logger LOG = LogManager.getLogger(TrackerSQL.class.getName());

    public boolean init() {
        try (InputStream in = config) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            this.connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return this.connection != null;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * Add new item
     *
     * @param item any item
     * @return item
     */
    @Override
    public Item add(Item item) {
        Item result = null;
        String sql = "INSERT INTO item(name, description, created) VALUES (?, ?, ?)";
        try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, item.getName());
            pstm.setString(2, item.getDesc());
            pstm.setTimestamp(3, new Timestamp(item.getCreate()));
            pstm.executeUpdate();
            try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setId(generatedKeys.getString(1));
                }
            }
            result = item;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Replace item
     *
     * @param id   any id
     * @param item any item
     * @return if replaced then true
     */
    @Override
    public boolean replace(String id, Item item) {
        boolean result = false;
        String sql = "UPDATE item SET name = ?, description = ?, created = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, item.getName());
            pstmt.setString(2, item.getDesc());
            pstmt.setTimestamp(3, new Timestamp(item.getCreate()));
            pstmt.setInt(4, Integer.valueOf(id));
            result = pstmt.executeUpdate() != 0;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public boolean delete(String id) {
        boolean result = false;
        String sql = "DELETE FROM item WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.valueOf(id));
            result = pstmt.executeUpdate() != 0;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Item[] findAll() {
        String sql = "SELECT id, name, description, created FROM item";
        return findBy(sql, null);
    }

    @Override
    public Item[] findByName(String key) {
        String sql = "SELECT id, name, description, created FROM item WHERE name = ?";
        return findBy(sql, x -> x.setString(1, key));
    }

    @Override
    public Item findById(String id) {
        String sql = "SELECT id, name, description, created FROM item WHERE id = ?";
        Item[] result = findBy(sql, x -> x.setInt(1, Integer.valueOf(id)));
        return result != null ? result[0] : null;
    }

    /**
     * General-purpose query method for findById, findByName, findAll
     *
     * @param sql any sql query
     * @param ps  functionality interface the same Consumer but can throws SQLException or NULL
     * @return array items
     */
    private Item[] findBy(String sql, ConsumerX<PreparedStatement> ps) {
        List<Item> items = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            if (ps != null) {
                ps.accept(pstmt);
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Item item = new Item(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTimestamp("created").getTime());
                item.setId(String.valueOf(rs.getInt("id")));
                items.add(item);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return items.size() != 0 ? items.toArray(new Item[items.size()]) : null;
    }

    /**
     * The same Consumer but can throws SQLException
     *
     * @param <T> for example  PreparedStatement
     */
    interface ConsumerX<T> {
        void accept(T obj) throws SQLException;
    }

}
