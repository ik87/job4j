package ru.job4j.var_search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class PhoneDictionary {
    private List<Person> persons = new ArrayList<>();

    public void add(Person person) {
        this.persons.add(person);
    }

    /**
     * get list all users who contains key in every field
     *
     * @param key search key
     * @return list proper users
     */
    public List<Person> find(String key) {
        List<Person> result = new ArrayList<>();
        for (var person : persons) {
            if (person.getName().contains(key) || person.getAddress().contains(key)
                    || person.getPhone().contains(key) || person.getSurname().contains(key)) {
                result.add(person);
            }
        }
        return result;
    }
}
