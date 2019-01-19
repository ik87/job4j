package ru.job4j.hashmap;

import java.util.HashMap;
import java.util.List;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class UserConvert {
    /**
     * Create HashMap base on the list users and them id
     *
     * @param list users list
     * @return hashMap
     */
    public HashMap<Integer, User> process(List<User> list) {
        HashMap<Integer, User> data = new HashMap<>();
        for (User user : list) {
            data.put(user.getId(), user);
        }
        return data;
    }
}
