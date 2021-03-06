package ru.job4j.web_app.persistent;

import ru.job4j.web_app.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $id
 * @since 0.1
 */
public class MemoryStore implements Store {

    private final static MemoryStore INSTANCE = new MemoryStore();

    private final Map<String, User> storage = new ConcurrentHashMap<>();

    private MemoryStore() {

    }

    public static MemoryStore getInstance() {
        return INSTANCE;
    }

    @Override
    public void add(User user) {
        storage.put(user.getId(), user);
    }

    @Override
    public void update(User user) {
        storage.put(user.getId(), user);
    }

    @Override
    public void delete(User user) {
        storage.remove(user.getId());
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public User findById(User user) {
        return storage.get(user.getId());
    }
}
