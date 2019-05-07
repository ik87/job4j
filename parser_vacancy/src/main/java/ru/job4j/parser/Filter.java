package ru.job4j.parser;

import java.util.Properties;
import java.util.function.Predicate;

public class Filter implements Predicate<String> {
    private Properties config;

    public Filter(Properties properties) {
        this.config = properties;
    }

    private String[] filters = config.getProperty("jdbc.filter").split(",");

    @Override
    public boolean test(String s) {
        boolean result = true;
        for (String filter : filters) {
            if (filter.startsWith("!")) {
                String except = filter.substring(1);
                if (s.contains(except)) {
                    result = false;
                    break;
                }
            } else if (!s.contains(filter)) {
                result = false;
                break;
            }
        }
        return result;
    }
}
