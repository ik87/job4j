package ru.job4j.tracker;

public class MenuOutException extends RuntimeException {
    MenuOutException() {
    }

    MenuOutException(String msg) {
        super(msg);
    }
}
