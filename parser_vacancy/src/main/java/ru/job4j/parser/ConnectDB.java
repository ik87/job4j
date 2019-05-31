package ru.job4j.parser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Define connector
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public interface ConnectDB {
    Connection getInstance() throws IOException, SQLException, ClassNotFoundException;
}
