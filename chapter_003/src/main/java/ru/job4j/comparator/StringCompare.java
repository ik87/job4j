package ru.job4j.comparator;

import java.util.Comparator;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $0.1$
 * @since 0.1
 */
public class StringCompare implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        int result = 0;
        int length = Math.min(o1.length(), o2.length());

        for (int i = 0; i < length; i++) {
            if (o1.charAt(i) != o2.charAt(i)) {
                result = Character.compare(o1.charAt(i), o2.charAt(i));
                break;
            }
        }
        if (result == 0) {
            result = Integer.compare(o1.length(), o2.length());
        }
        return result;

    }
}
