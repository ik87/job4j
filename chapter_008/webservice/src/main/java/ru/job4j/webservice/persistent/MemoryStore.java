package ru.job4j.webservice.persistent;

import ru.job4j.webservice.models.Role;
import ru.job4j.webservice.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryStore implements Store {


    private final static MemoryStore INSTANCE = new MemoryStore();

    private final Map<Integer, User> users = new ConcurrentHashMap<>();
    private final Map<Integer, Role> roles = new ConcurrentHashMap<>();

    private final AtomicInteger id = new AtomicInteger(1);

    private MemoryStore() {

    }

    public static MemoryStore getInstance() {
        return INSTANCE;
    }

    @Override
    public void add(User user) {
        users.computeIfAbsent(id.getAndIncrement(), k -> {
            user.setId(k);
            return user;
        });
    }

    @Override
    public void update(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public void delete(User user) {
        users.remove(user.getId());
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User findById(User user) {
        return users.get(user.getId());
    }

    @Override
    public List<Role> getRoles() {
        return new ArrayList<>(roles.values());
    }

    @Override
    public User findByLogin(User user) {
        User usr = null;
        for( User u : users.values()) {
            if(Objects.equals(user.getLogin(), user.getLogin())) {
                usr = u;
                break;
            }
        }
        return usr;
    }

    @Override
    public boolean ifExist(User user) {
        return users.containsKey(user.getId());
    }


}
