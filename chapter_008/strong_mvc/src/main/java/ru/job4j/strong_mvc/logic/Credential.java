package ru.job4j.strong_mvc.logic;

import ru.job4j.strong_mvc.model.User;

import java.util.List;
import java.util.Objects;

public class Credential {
    Validate validate = ValidateService.getInstance();
    User user;

    public boolean isCredential(String login, String password) {
        boolean result = false;
        List<User> users = validate.findAll();
        for (User user : users) {
            if (Objects.equals(login, user.getLogin())) {
                if (Objects.equals(password, user.getPassword())) {
                    this.user = user;
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    public User getUser() {
        return user;
    }
}
