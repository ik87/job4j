package ru.job4j.strong_mvc.logic;

public interface Credential {
    boolean isValid(String login, String password);
}
