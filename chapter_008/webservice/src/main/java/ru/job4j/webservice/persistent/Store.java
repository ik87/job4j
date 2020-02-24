package ru.job4j.webservice.persistent;

import ru.job4j.webservice.models.Role;
import ru.job4j.webservice.models.User;

import java.util.List;

public interface Store {
    void add(User user);

    void update(User user);

    void delete(User user);

    List<User> findAll();

    User findById(User user);

    boolean ifExist(User user);

    List<Role> getRoles();

    User findByLogin(User user);

    User findByLoginAndPassword(User user);
}
