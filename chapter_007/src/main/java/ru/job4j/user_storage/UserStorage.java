package ru.job4j.user_storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */

@ThreadSafe
public class UserStorage {
    private Map<Integer, User> userHashMap = new HashMap<>();
    @GuardedBy("monitor")
    private final Object monitor = new Object();

    boolean add(User user) {
        synchronized (monitor) {
            boolean added = false;
            if (!userHashMap.containsKey(user.getId())) {
                userHashMap.put(user.getId(), user);
                added = true;
            }
            return added;
        }
    }

    boolean update(User user) {
        synchronized (monitor) {
            boolean added = false;
            if (userHashMap.containsKey(user.getId())) {
                userHashMap.put(user.getId(), user);
                added = true;
            }
            return added;
        }
    }

    boolean delete(User user) {
        synchronized (monitor) {
            return userHashMap.remove(user.getId(), user);
        }
    }

    boolean transfer(int fromid, int toid, int amount) {
        synchronized (monitor) {
            boolean success = false;
            User userA = userHashMap.get(fromid);
            User userB = userHashMap.get(toid);
            if (userA != null && userB != null && userA.getAmount() - amount >= 0) {
                userA.setAmount(userA.getAmount() - amount);
                 userB.setAmount(userB.getAmount() + amount);
                success = true;
            }
            return success;
        }
    }

    User getUser(int id) {
        return userHashMap.get(id);
    }
}
