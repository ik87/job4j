package ru.job4j.generics;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class StoreTest {
    Store<User> userStore;
    Store<Role> roleStore;

    @Before
    public void initUser() {
        userStore = new UserStore(3);
        userStore.add(new User("Max"));
        userStore.add(new User("Den"));
        userStore.add(new User("Joy"));

        roleStore = new RoleStore(3);
        roleStore.add(new Role("Rider"));
        roleStore.add(new Role("Manager"));
        roleStore.add(new Role("Programmer"));
    }

    @Test
    public void whenAddToStorageThenAdded() {
        assertThat(userStore.toString(), is("[Max, Den, Joy]"));
        assertThat(roleStore.toString(), is("[Rider, Manager, Programmer]"));

    }

    @Test
    public void whenRemoveThenRemoved() {
        boolean userDeleted = userStore.delete("Max");
        boolean roleDeleted = roleStore.delete("Rider");
        assertThat(userStore.toString(), is("[Den, Joy]"));
        assertThat(userDeleted, is(true));
        assertThat(roleStore.toString(), is("[Manager, Programmer]"));
        assertThat(roleDeleted, is(true));
    }

    @Test
    public void whenSetThenChange() {
        boolean userChanged = userStore.replace("Joy", new User("Sem"));
        boolean roleChanged = roleStore.replace("Manager", new Role("Lawyer"));
        assertThat(userStore.toString(), is("[Max, Den, Sem]"));
        assertThat(userChanged, is(true));
        assertThat(roleStore.toString(), is("[Rider, Lawyer, Programmer]"));
        assertThat(roleChanged, is(true));
    }

    @Test
    public void whenFindThenFound() {
        User user = userStore.findById("Den");
        assertThat(user.toString(), is("Den"));

        Role role = roleStore.findById("Programmer");
        assertThat(role.toString(), is("Programmer"));
    }

}