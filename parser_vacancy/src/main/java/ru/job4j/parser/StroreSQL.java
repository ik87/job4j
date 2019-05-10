package ru.job4j.parser;

import java.sql.*;
import java.text.ParseException;
import java.util.Collection;

public class StroreSQL implements AutoCloseable {
    private Connection connection;
    private ConvertDate convertDate = new ConvertDate();

    public StroreSQL(Connection connection) {
        this.connection = connection;
    }

    public void add(Collection<Vacancy> vacancies) throws SQLException, ParseException {
        final String sql = "INSERT INTO vacancy_sql_ru (name, descriprion, link, updated) VALUES (?, ?, ?, ?)"
                + "ON CONFLICT (name) DO UPDATE SET updated = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Vacancy vacancy : vacancies) {
                pstmt.setString(1, vacancy.name);
                pstmt.setString(2, vacancy.description);
                pstmt.setString(3, vacancy.link);
                pstmt.setTimestamp(4, new Timestamp(convertDate.convert(vacancy.date)));
                pstmt.setTimestamp(5, new Timestamp(convertDate.convert(vacancy.date)));
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
        timeUpdate();
    }
    private void timeUpdate() throws SQLException {
        String sql = "INSERT INTO last_update (updated) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setTimestamp(1 ,  new Timestamp(convertDate.currentTime()));
            pstmt.executeUpdate();
        }
    }

    public Long lastUpdate() throws SQLException {
        Long time = -1L;
        String sql = "SELECT max(updated) as max FROM last_update";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                time = rs.getTimestamp("max").getTime();
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
