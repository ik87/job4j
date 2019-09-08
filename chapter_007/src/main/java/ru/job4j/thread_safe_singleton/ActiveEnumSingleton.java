package ru.job4j.thread_safe_singleton;

/**
 * Active singleton, prefer used when need init database and cache
 */
public enum ActiveEnumSingleton {
    INSTANCE;

    public Item add(Item model) {
        return model;
    }

    public static void main(String[] args) {
        ActiveEnumSingleton enumSingleton = ActiveEnumSingleton.INSTANCE;
        System.out.print("hello enum");
    }
}
