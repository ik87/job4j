package ru.job4j.algorithm;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SortUserTest {
    private SortUser sortUser = new SortUser();
    private List<User> users;

    @Before
    public void init() {
        users = new ArrayList<>();
        users.add(new User("Greg", 30));
        users.add(new User("Fry", 43));
        users.add(new User("Ban", 22));
        users.add(new User("Dan", 42));
        users.add(new User("Ann", 23));
        users.add(new User("Ann", 11));
    }

    @Test
    public void whenPutUnsortedListThenGetSorted() {
        Set<User> set = sortUser.sort(users);
        List<String> result = new ArrayList<>();
        set.forEach(x-> result.add(x.getName()));
        String[] expected = {"Ann", "Ban", "Ann",  "Greg", "Dan", "Fry"};

        assertThat(result.toArray(), is(expected));
    }
}
