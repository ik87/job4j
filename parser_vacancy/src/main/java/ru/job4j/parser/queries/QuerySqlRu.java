package ru.job4j.parser.queries;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.parser.StorageDB;
import ru.job4j.parser.entities.EntitySqlRu;
import ru.job4j.parser.utils.UtilsRu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

public class QuerySqlRu extends StorageDB<EntitySqlRu> {
    private static final Logger LOG = LogManager.getLogger(QuerySqlRu.class.getName());
    private UtilsRu utilsRu = new UtilsRu();
    public QuerySqlRu(Connection connection) {
        super(connection);
    }

    @Override
    protected String sqlQuery() {
        return "INSERT INTO vacancy_sql_ru (name, description, link, updated) VALUES (?, ?, ?, ?)"
                + "ON CONFLICT (name) DO UPDATE SET updated = ?";
    }

    @Override
    protected void putEntities(PreparedStatement pstmt, EntitySqlRu entity) throws Exception {
            pstmt.setString(1, entity.name);
            pstmt.setString(2, entity.desc);
            pstmt.setString(3, entity.link);
            pstmt.setTimestamp(4, new Timestamp(entity.date));
            pstmt.setTimestamp(5, new Timestamp(entity.date));
    }


}
