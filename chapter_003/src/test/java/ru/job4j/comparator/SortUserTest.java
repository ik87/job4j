package ru.job4j.comparator;

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
        users.add(new User("Daivat", 8));
        users.add(new User("Babar", 22));
        users.add(new User("Daivat", 42));
        users.add(new User("Ann", 23));
        users.add(new User("Abelena", 11));
    }

    /**
     * Convert User to String
     *
     * @param user List users
     * @return List
     */
    private List<String> convertUserToString(Iterable<User> user) {
        List<String> result = new ArrayList<>();
        user.forEach(x -> result.add(String.format("%s %s", x.getName(), x.getAge())));
        return result;
    }

    @Test
    public void whenPutUnsortedListThenGetSortedByAge() {
        Set<User> set = sortUser.sort(users);
        List<String> result = convertUserToString(set);
        String[] expected = {"Daivat 8", "Abelena 11", "Babar 22", "Ann 23", "Greg 30", "Daivat 42", "Fry 43"};

        assertThat(result.toArray(), is(expected));
    }


    @Test
    public void whenPutUnsortedListThenGetSortedByNameLength() {
        List<User> list = sortUser.sortNameLength(users);
        List<String> result = convertUserToString(list);
        String[] expected = {"Fry 43", "Ann 23", "Greg 30", "Babar 22", "Daivat 8", "Daivat 42", "Abelena 11"};

        assertThat(result.toArray(), is(expected));
    }

    @Test
    public void whenPutUnsortedListThenGetSortedByNameAndAge() {
        List<User> list = sortUser.sortByAllFields(users);
        List<String> result = convertUserToString(list);
        String[] expected = {"Abelena 11", "Ann 23", "Babar 22", "Daivat 8", "Daivat 42", "Fry 43", "Greg 30"};

        assertThat(result.toArray(), is(expected));
    }
}
