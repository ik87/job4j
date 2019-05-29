package ru.job4j.parser.queries;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.parser.StorageDB;
import ru.job4j.parser.entities.EntityAvitoRu;
import ru.job4j.parser.utils.UtilsAvitoRu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

public class QueryAvitoRu extends StorageDB<EntityAvitoRu> {
    private static final Logger LOG = LogManager.getLogger(QuerySqlRu.class.getName());
    private UtilsAvitoRu utils = new UtilsAvitoRu();


    public QueryAvitoRu(Connection connection) {
        super(connection);
    }

    @Override
    protected String sqlQuery() {
        return "INSERT INTO avito (name, description, link, updated, price) VALUES (?, ?, ?, ?, ?)"
                + "ON CONFLICT (name) DO NOTHING";
    }

    @Override
    protected void putEntities(PreparedStatement pstmt, EntityAvitoRu entity) throws Exception {
        pstmt.setString(1, entity.name);
        pstmt.setString(2, entity.desc);
        pstmt.setString(3, entity.link);
        pstmt.setTimestamp(4, new Timestamp(entity.date));
        pstmt.setString(5, entity.price);
    }
}