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
        final String sql = "INSERT INTO vacancy (name, text, link, updated) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Vacancy vacancy : vacancies) {
                pstmt.setString(1, vacancy.name);
                pstmt.setString(2, vacancy.description);
                pstmt.setString(3, vacancy.link);
                pstmt.setTimestamp(4, new Timestamp(convertDate.convert(vacancy.date)));
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    public Long getLastUpdate() throws SQLException {
        Long time = -1L;
        String sql = "SELECT max(updated) as max FROM vacancy";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                time = rs.getLong("max");
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
