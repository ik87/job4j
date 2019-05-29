package ru.job4j.parser.queries;

import org.junit.Test;
import ru.job4j.parser.Config;
import ru.job4j.parser.Parser;
import ru.job4j.parser.Utils;
import ru.job4j.parser.entities.EntitySqlRu;
import ru.job4j.parser.utils.UtilsSqlRu;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static ru.job4j.parser.StorageDB.init;

public class QuerySqlRuTest {

    @Test
    public void putValueToDBThenGetCount() throws Exception {
        EntitySqlRu expected = new EntitySqlRu();


        expected.name = "Требуется java разработчик";
        expected.desc = "Требуется java разработчик junior";
        expected.link = "vacansyPage.html";
        expected.date = new Utils().
                dateToMillis("01 01 19, 00:00", TimeZone.getTimeZone("Europe/Moscow"));

        int result = 0;

        //Connection connection;
        Connection connection = ConnectionRollback.create(init(config()));
        try (QuerySqlRu querySqlRu = new QuerySqlRu(connection)) {
            querySqlRu.add(List.of(expected));
            result = querySqlRuGetCount(connection);
            assertThat(1, is(result));
        }


    }

    /**
     * Get count of rows
     * @param connection connection
     * @return size table
     * @throws SQLException
     */
    public int querySqlRuGetCount(Connection connection) throws SQLException {
        int size = 0;
        String sql = "SELECT count(*) as count FROM vacancy_sql_ru";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                size = rs.getInt("count");
            }
        }
        return size;
    }

    //get Properties
    public Config config() throws IOException {
        Properties properties;
        Config config = new Config();
        try (InputStream in = Parser.class.getClassLoader().getResourceAsStream("app.properties")) {
            properties = new Properties();
            properties.load(in);
        }
        config.setProp(properties);
        config.setDB();

        return config;
    }

    /**
     * Connection, which rollback all commits.
     * It is used for integration test.
     */
    public static class ConnectionRollback {

        /**
         * Create connection with autocommit=false mode and rollback call, when connection is closed.
         *
         * @param connection connection.
         * @return Connection object.
         * @throws java.sql.SQLException possible exception.
         */
        public static Connection create(Connection connection) throws SQLException {
            connection.setAutoCommit(false);
            return (Connection) Proxy.newProxyInstance(
                    ConnectionRollback.class.getClassLoader(),
                    new Class[]{Connection.class},
                    (proxy, method, args) -> {
                        Object rsl = null;
                        if ("close".equals(method.getName())) {
                            //  connection.commit();
                            connection.rollback();
                            connection.close();
                        } else {
                            rsl = method.invoke(connection, args);
                        }
                        return rsl;
                    }
            );
        }
    }
}