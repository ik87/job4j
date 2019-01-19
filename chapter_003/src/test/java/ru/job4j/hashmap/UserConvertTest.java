package ru.job4j.hashmap;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Kosolapov Ilya(d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class UserConvertTest {
    List<User> users;
    UserConvert convert = new UserConvert();

    @Before
    public void init() {
        users = new ArrayList<>();
        users.add(new User(1, "Jake", "New York"));
        users.add(new User(2, "Kate", "Boston"));
        users.add(new User(3, "Make", "Texas"));
        users.add(new User(4, "Make", "Texas"));

    }

    @Test
    public void whenListUsersPutThenHashMapUsersGet() {
        HashMap result = convert.process(users);
        assertThat(result.values().toArray(), is(users.toArray()));
    }

    @Test
    public void whenListUsersPutAndAddEachOneThenNotEqualList() {
        users.add(new User(2, "Dan", "Texas"));
        HashMap result = convert.process(users);
        assertThat(result.values().toArray(), not(users.toArray()));
    }

}
