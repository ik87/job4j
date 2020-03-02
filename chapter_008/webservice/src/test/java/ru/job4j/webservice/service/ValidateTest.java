package ru.job4j.webservice.service;

import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.webservice.models.Role;
import ru.job4j.webservice.models.User;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ValidateTest {
    @Ignore
    @Test
    public void whenSearchLonginThenGetUser() {
        Validate validate = ValidateService.getInstance();
        User user = new User();
        user.setLogin("John");
        User result = validate.findByLogin(user);
        assertThat(result.getPassword(), is("321"));
        assertThat(result.getRole().getRole(), is("user"));
    }
    @Ignore
    @Test
    public void whenSearchIdThenGetUser() {
        Validate validate = ValidateService.getInstance();
        User user = new User();
        user.setId(1);
        User result = validate.findById(user);
        assertThat(result.getPassword(), is("toor"));
    }
    @Ignore
    @Test
    public void whenCRUDUserThenOK() {
        User user = new User();
        Role role = new Role();



        Validate validate = ValidateService.getInstance();

        //add user
        user.setLogin("Foo");
        user.setEmail("foo@gmail.com");
        user.setPassword("222");
        role.setId(2);
        user.setRole(role);
        validate.add(user);

        User getResult = validate.findByLogin(user);

        assertThat(getResult.getRole().getRole(), is("user"));
        assertThat(getResult.getRole().getId(), is(2));
        assertThat(getResult.getLogin(), is("Foo"));
        assertThat(getResult.getEmail(), is("foo@gmail.com"));
        assertThat(getResult.getPassword(), is("222"));
        assertNotNull(getResult.getCreated());

        //update user
        getResult.setLogin("Bar");
        getResult.setPassword("321");
        getResult.setEmail("bar@gmail.com");
        getResult.getRole().setId(1);

        validate.update(getResult);

        User getNewResult =  validate.findById(getResult);

        assertThat(getNewResult.getRole().getRole(), is("admin"));
        assertThat(getNewResult.getRole().getId(), is(1));
        assertThat(getNewResult.getLogin(), is("Bar"));
        assertThat(getNewResult.getEmail(), is("bar@gmail.com"));
        assertThat(getNewResult.getPassword(), is("321"));
        assertNotNull(getNewResult.getCreated());

        //delete user
        validate.delete(getResult);
        User deleteResult = validate.findByLogin(user);
        assertNull(deleteResult);
    }
    @Ignore
    @Test
    public void whenGetUserByLoginAndPasswordThenValid() {
        Validate validate = ValidateService.getInstance();
        User user = new User();
        user.setLogin("John");
        user.setPassword("321");
        User result = validate.findByLoginAndPassword(user);
        assertNotNull(result);
    }
    @Ignore
    @Test
    public void whenGetUserByLoginAndPasswordThenInValid() {
        Validate validate = ValidateService.getInstance();
        User user = new User();
        user.setLogin("John");
        user.setPassword("123");
        User result = validate.findByLoginAndPassword(user);
        assertNull(result);
    }

}