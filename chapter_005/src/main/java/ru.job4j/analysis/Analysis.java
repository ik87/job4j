package ru.job4j.analysis;

import java.util.*;

/**
 * This class counting  difference between two lists
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 0.1
 */
public class Analysis {
    /**
     * Calc number of difference by id between two lists
     *
     * @param previous previous list users
     * @param current  current list users
     * @return number difference
     */
    private int diffById(List<User> previous, List<User> current) {
        Set<User> cur = new HashSet<>(current);
        cur.removeAll(previous);
        return cur.size();
    }

    /**
     * Calc number of changed users by same ids and different names
     *
     * @param previous previous list users
     * @param current  current list users
     * @return number difference
     */
    private int changed(List<User> previous, List<User> current) {
        int i = 0;
        Set<User> prev = new TreeSet<>(Comparator.comparing(User::getId));
        Set<User> cur = new TreeSet<>(Comparator.comparing(User::getId));
        prev.addAll(previous);
        cur.addAll(current);
        cur.retainAll(previous);
        prev.retainAll(current);

        Iterator<User> p = prev.iterator();
        Iterator<User> c = cur.iterator();

        while (p.hasNext() && c.hasNext()) {
            User a = p.next();
            User b = c.next();
            if (a.equals(b) && !a.getName().equals(b.getName())) {
                i++;
            }
        }
        return i;
    }

    /**
     * General method that join all together then give general statistic
     *
     * @param previous previous list users
     * @param current  current list users
     * @return Info instance
     */
    public Info diff(List<User> previous, List<User> current) {
        Info info = new Info();
        info.added = diffById(previous, current);
        info.deleted = diffById(current, previous);
        info.changed = changed(previous, current);

        return info;
    }

    public static class User {
        private int id;
        private String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            User user = (User) o;
            return id == user.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public static class Info {
        int added;
        int changed;
        int deleted;

    }
}