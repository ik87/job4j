package ru.job4j.web_app_arhitecture_jsp.logic;

import ru.job4j.web_app_arhitecture_jsp.model.User;

import java.util.List;

/**
 * This interface responsible for validation CRUD operations
 * Define logic layer, interact with persistent layer and presentation layer.
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $id
 * @since 0.1
 */
public interface Validate {
    boolean add(User user);

    boolean update(User user);

    boolean delete(User user);

    List<User> findAll();

    User findById(User user);
}
