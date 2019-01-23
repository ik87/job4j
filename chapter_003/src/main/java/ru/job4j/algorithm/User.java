package ru.job4j.algorithm;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class User implements Comparable<User> {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    /**
     * compare by ages if names the same
     *
     */
    @Override
    public int compareTo(User o) {

        return name.equals(o.name) ? Integer.compare(age, o.age) : name.compareTo(o.name);
    }

}
