package ru.job4j.strong_mvc.logic;

public class CredentialService implements Credential {
    @Override
    public boolean isValid(String login, String password) {
        return false;
    }
}
