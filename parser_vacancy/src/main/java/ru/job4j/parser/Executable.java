package ru.job4j.parser;

public interface Executable {
     Class<? extends StorageDB> database();
     Parser parser();

}
