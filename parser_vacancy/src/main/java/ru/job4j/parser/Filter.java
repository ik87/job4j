package ru.job4j.parser;

import java.util.function.Predicate;

/**
 * This class used for filtering parsed data
 * Set word that could be include or exclude in the found
 * The word that start with '!' will be exclude
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class Filter implements Predicate<String> {
    private String[] filters;
    public Filter(String config) {
        this.filters = config.split(",");
    }

    @Override
    public boolean test(String s) {
        s = s.toLowerCase();
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
