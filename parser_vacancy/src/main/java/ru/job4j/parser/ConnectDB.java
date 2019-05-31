package ru.job4j.parser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectDB {
    Connection getInstance() throws IOException, SQLException, ClassNotFoundException;
}
