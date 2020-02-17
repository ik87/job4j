package ru.job4j.strong_mvc.logic;

import ru.job4j.strong_mvc.model.Role;
import ru.job4j.strong_mvc.model.User;

import java.util.List;

/**
 * This interface responsible for validation CRUD operations
 * Define logic layer, interact with persistent layer and controller layer.
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $id
 * @since 0.1
 */
public interface Validate {
    boolean add(User user);

    boolean update(User user);

    boolean delete(User user);

    List<Role> getRoles();

    List<User> findAll();

    User findById(User user);
}
