package ru.job4j.parser;

import java.sql.*;
import java.text.ParseException;
import java.util.Collection;
/**
 * Main class for work with DB, save vacancy, get last date update
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class StoreSQL implements AutoCloseable {
    private Connection connection;
    private Converter converter = new Converter();

    public StoreSQL(Connection connection) {
        this.connection = connection;
    }

    /**
     * Add vacancy to table if vacancy is exist then update its date
     * @param vacancies Collection parsed vacancies
     * @throws SQLException if problems with DB then throw exception
     * @throws ParseException if problem with parsing time then throw exception
     */
    public void add(Collection<Vacancy> vacancies) throws SQLException, ParseException {
        final String sql = "INSERT INTO vacancy_sql_ru (name, description, link, updated) VALUES (?, ?, ?, ?)"
                + "ON CONFLICT (name) DO UPDATE SET updated = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Vacancy vacancy : vacancies) {
                pstmt.setString(1, vacancy.name);
                pstmt.setString(2, vacancy.description);
                pstmt.setString(3, vacancy.link);
                pstmt.setTimestamp(4, new Timestamp(converter.date(vacancy.date)));
                pstmt.setTimestamp(5, new Timestamp(converter.date(vacancy.date)));
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }

         //save current time in the table
        timeUpdate();
    }

    /**
     * After call this method save current time in the table 'last_update'
     * @throws SQLException if problems with DB then throw exception
     */
    private void timeUpdate() throws SQLException {
        String sql = "INSERT INTO last_update (updated) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setTimestamp(1, new Timestamp(converter.currentTime()));
            pstmt.executeUpdate();
        }
    }

    /**
     * Get last update date
     * @return  time in Long format or -1L if DB is empty
     * @throws SQLException if problems with DB then throw exception
     */
    public Long lastUpdate() throws SQLException {
        Long time = -1L;
        String sql = "SELECT max(updated) as max FROM last_update";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Timestamp last = rs.getTimestamp("max");
                time = last == null ? time : last.getTime();
            }

        }
        return time;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}
