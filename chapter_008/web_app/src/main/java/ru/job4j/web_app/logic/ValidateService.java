package ru.job4j.web_app.logic;

import ru.job4j.web_app.model.User;
import ru.job4j.web_app.persistent.MemoryStore;
import ru.job4j.web_app.persistent.Store;

import java.util.List;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $id
 * @since 0.1
 */
public class ValidateService implements Validate {
    private final Store store = MemoryStore.getInstance();

    private static final ValidateService INSTANCE = new ValidateService();

    private ValidateService() {
    }

    public static ValidateService getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(User user) {
        boolean result = false;
        if (store.findById(user) == null
                && !hasEmptyString(user)) {
            store.add(user);
            result = true;
        }
        return result;
    }

    @Override
    public boolean update(User user) {
        boolean result = false;
        if (store.findById(user) != null) {
            store.update(user);
            result = true;
        }
        return result;
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
        return user.getId().isEmpty()
                && user.getName().isEmpty()
                && user.getEmail().isEmpty()
                && user.getCreateDate().isEmpty()
                && user.getLogin().isEmpty();
    }
}
