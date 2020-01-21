package ru.job4j.strong_mvc.logic;

import ru.job4j.strong_mvc.model.User;
import ru.job4j.strong_mvc.persistent.DbStore;
import ru.job4j.strong_mvc.persistent.MemoryStore;
import ru.job4j.strong_mvc.persistent.Store;

import java.util.List;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $id
 * @since 0.1
 */
public class ValidateService implements Validate {
    private final Store store = MemoryStore.getInstance();
    //private final Store store = DbStore.getInstance();

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
        if (store.ifExist(user)  && hasEmptyString(user)) {
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
        return  !user.getName().isEmpty()
                && !user.getEmail().isEmpty()
                && !user.getCreateDate().isEmpty()
                && !user.getLogin().isEmpty();
    }
}
