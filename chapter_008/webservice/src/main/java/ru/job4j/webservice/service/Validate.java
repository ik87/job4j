package ru.job4j.webservice.service;

import ru.job4j.webservice.models.User;

import java.util.List;

public interface Validate {
    boolean add(User user);

    boolean update(User user);

    boolean delete(User user);

    List<User> findAll();

    User findById(User user);

    User findByLogin(User user);
}
