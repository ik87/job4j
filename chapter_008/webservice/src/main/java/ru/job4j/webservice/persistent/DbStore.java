package ru.job4j.webservice.persistent;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.webservice.models.Role;
import ru.job4j.webservice.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbStore implements Store{
    @Override
    public void add(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(User user) {
        return null;
    }

    @Override
    public boolean ifExist(User user) {
        return false;
    }

    @Override
    public List<Role> getRoles() {
        return null;
    }
}
