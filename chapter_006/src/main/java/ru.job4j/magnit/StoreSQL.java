package ru.job4j.magnit;

import java.sql.*;
import java.util.*;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class StoreSQL implements AutoCloseable {
    private final Config config;
    private Connection connect;

    /**
     * Connect to  database
     */
    private void init() {
        try {
            this.connect = DriverManager.getConnection(config.get("url"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public StoreSQL(Config config) {
        this.config = config;
        init();
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Entry (field integer)";
        try (Statement st = connect.createStatement()) {
            st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS Entry";
        try (Statement st = connect.createStatement()) {
            st.executeUpdate(sql);
            st.executeUpdate("vacuum");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generate N times and fill table with transaction
     *
     * @param size
     * @throws SQLException
     */
    public void generate(int size) throws SQLException {
        dropTable();
        createTable();
        String sql = "INSERT INTO Entry(field) VALUES (?)";
        connect.setAutoCommit(false);
        try (PreparedStatement pstm = connect.prepareStatement(sql)) {

            for (int i = 0; i < size; i++) {
                pstm.setInt(1, i + 1);
                pstm.executeUpdate();
            }
        } catch (SQLException e) {
            connect.rollback();
            connect.setAutoCommit(true);
            throw e;
        }
        connect.commit();
        connect.setAutoCommit(true);
    }

    public List<Entry> load() {
        List<Entry> list = new ArrayList<>();
        String sql = "SELECT field FROM entry";
        try (PreparedStatement pstm = connect.prepareStatement(sql)) {
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(new Entry(rs.getInt("field")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void close() throws Exception {
        if (connect != null) {
            connect.close();
        }
    }
}