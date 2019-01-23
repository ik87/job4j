package ru.job4j.algorithm;

import java.util.*;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class SortUser {
    /**
     * This method accept list and return ordering set
     * @param users list
     * @return sets
     */
    public Set<User> sort(List<User> users) {
        /*
        NavigableSet<User> data = new TreeSet<>(Comparator.comparing(User::getAge));
        data.addAll(users);
        return data;
        */
        return  new TreeSet<User>(users);

    }
}
