package ru.job4j.webservice.service;

import ru.job4j.webservice.models.User;

import java.util.List;

public interface Validate {
    boolean add(User user);

    boolean update(User user);

    /**
     * Update user through merge  oldUser with newUser
     * Attention! oldUser will also be changed!
     * if you use Session, in session user will be changed!
     * @param oldUser old user that was before
     * @param newUser new user
     * @return get condition operation
     */
    boolean update(User oldUser, User newUser);

    boolean delete(User user);

    List<User> findAll();

    User findById(User user);

    User findByLogin(User user);

    /**
     * check password and login
     * @param user comparable user
     * @return get null if login and\or password isn't correct
     */
    User findByLoginAndPassword(User user);
}
