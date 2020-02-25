package ru.job4j.webservice.service;

import ru.job4j.webservice.models.User;
import ru.job4j.webservice.persistent.DbStore;
import ru.job4j.webservice.persistent.Store;

import java.util.List;

public class ValidateService implements Validate {
    private final Store store = DbStore.getInstance();

    private static final ValidateService INSTANCE = new ValidateService();

    private ValidateService() {
    }

    public static ValidateService getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(User user) {
        boolean result = false;
        if (hasEmptyString(user)) {
            store.add(user);
            result = true;
        }
        return result;
    }

    @Override
    public boolean update(User user) {
        boolean result = false;
        if (store.ifExist(user) && hasEmptyString(user)) {
            store.update(user);
            result = true;
        }
        return result;
    }

    @Override
    public boolean update(User oldUser, User newUser) {

        if (!isEmpty(newUser.getPassword())) {
            oldUser.setPassword(newUser.getPassword());
        }

        if (!isEmpty(newUser.getLogin())) {
            oldUser.setLogin(newUser.getLogin());
        }

        if (!isEmpty(newUser.getEmail())) {
            oldUser.setEmail(newUser.getEmail());
        }

        if (newUser.getPhoto() != null) {
            oldUser.setPhoto(newUser.getPhoto());
        }

        if (newUser.getRole() != null) {
            oldUser.setRole(oldUser.getRole());
        }

        return update(oldUser);
    }

    @Override
    public boolean delete(User user) {
        boolean result = false;
        if (store.findById(user) != null) {
            store.delete(user);
            result = true;
        }
        return result;
    }

    @Override
    public List<User> findAll() {
        return store.findAll();
    }

    @Override
    public User findById(User user) {
        return store.findById(user);
    }

    private boolean hasEmptyString(User user) {
        return !user.getLogin().isEmpty()
                && !user.getEmail().isEmpty()
                && !user.getPassword().isEmpty();
    }

    @Override
    public User findByLogin(User user) {
        return store.findByLogin(user);
    }

    @Override
    public User findByLoginAndPassword(User user) {
        return store.findByLoginAndPassword(user);
    }

    /**
     * check string on null and empty
     * @param str checked string
     * @return true if empty
     */
    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
