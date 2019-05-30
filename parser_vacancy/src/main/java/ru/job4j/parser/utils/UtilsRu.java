package ru.job4j.parser.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.parser.Utils;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.TimeZone;

/**
 * This class used for work with time. Converting or get current time
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class UtilsRu extends Utils {

    /**
     * Convert parsed dateToMillis to Long
     * for example:
     * Сегодня 14:06  -> 1557831960000
     * Вчера 14:06  -> 1557745560000
     *
     * @param date dateToMillis in string format
     * @return dateToMillis in Long format
     * @throws ParseException
     */
    public Long dateToMillisRus(String date, TimeZone timeZone, String template) {
        Long time = 0L;
        if (date.toLowerCase().contains("сегодня")) {
            time = datePeriod(date, 0, timeZone);
        } else if (date.toLowerCase().contains("вчера")) {
            time = datePeriod(date, -1, timeZone);
        } else {
            time = dateToMillis(date, timeZone, template);
        }
        return time;
    }
}


