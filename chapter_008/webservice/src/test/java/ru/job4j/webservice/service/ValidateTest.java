package ru.job4j.webservice.service;

import org.junit.Test;
import ru.job4j.webservice.models.User;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ValidateTest {

    @Test
    public void whenSearchLonginThenGetUser() {
        Validate validate = ValidateService.getInstance();
        User user = new User();
        user.setLogin("John");
        User result = validate.findByLogin(user);
        assertThat(result.getPassword(), is("321"));
        assertThat(result.getRole().getRole(), is("user"));
    }

    @Test
    public void whenSearchIdThenGetUser() {
        Validate validate = ValidateService.getInstance();
        User user = new User();
        user.setId(1);
        User result = validate.findById(user);
        assertThat(result.getPassword(), is("toor"));
    }

}