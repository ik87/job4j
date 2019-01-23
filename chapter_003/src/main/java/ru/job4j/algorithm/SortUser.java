package ru.job4j.algorithm;

import java.util.*;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class SortUser {
    /**
     * This method accept list and return ordered set
     *
     * @param users list
     * @return sets
     */
    public Set<User> sort(List<User> users) {
        /*
        NavigableSet<User> data = new TreeSet<>(Comparator.comparing(User::getAge));
        data.addAll(users);
        return data;
        */
        return new TreeSet<User>(users);

    }

    /**
     * This method accept list and return ordering by name list
     *
     * @param list list
     * @return sets
     */
    public List<User> sortNameLength(List<User> list) {
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return Integer.compare(o1.getName().length(), o2.getName().length());
            }
        });
        return list;
    }

    /**
     * This method accept list and return ordering by ages if names are same
     *
     * @param list list
     * @return sets
     */
    public List<User> sortByAllFields(List<User> list) {
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getName().equals(o2.getName())
                        ? Integer.compare(o1.getAge(), o2.getAge()) : o1.getName().compareTo(o2.getName());
            }
        });
        return list;
    }
}
