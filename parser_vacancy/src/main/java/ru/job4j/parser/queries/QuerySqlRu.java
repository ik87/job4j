package ru.job4j.parser.queries;

import ru.job4j.parser.StorageDB;
import ru.job4j.parser.entities.EntitySqlRu;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Child class for work with Database, this class override some methods
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class QuerySqlRu extends StorageDB<EntitySqlRu> {

    @Override
    protected String sqlInsertQuery() {
        return "INSERT INTO vacancy_sql_ru (name, description, link, updated) VALUES (?, ?, ?, ?)"
                + "ON CONFLICT (name) DO UPDATE SET updated = ?";
    }

    @Override
    protected void putEntities(PreparedStatement pstmt, EntitySqlRu entity) throws SQLException {
            pstmt.setString(1, entity.getName());
            pstmt.setString(2, entity.getDesc());
            pstmt.setString(3, entity.getLink());
            pstmt.setTimestamp(4, new Timestamp(entity.getDate()));
            pstmt.setTimestamp(5, new Timestamp(entity.getDate()));
    }


}
