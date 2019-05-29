package ru.job4j.parser.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.parser.Utils;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * This class used for work with time. Converting or get current time
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class UtilsSqlRu extends Utils {
    private final static Logger LOG = LogManager.getLogger(UtilsSqlRu.class.getName());
    private String[] shortMonths = {
            "янв", "фев", "мар", "апр", "май", "июн",
            "июл", "авг", "сен", "окт", "ноя", "дек"};
    private DateFormatSymbols dfs = DateFormatSymbols.getInstance(LOCALE);
    private static final Locale LOCALE = new Locale("ru");

    /**
     * Convert parsed dateToMillis to Long
     * for example:
     *          29 апр 19, 14:06 -> 1556535960000
     *          сегодня, 14:06  -> 1557831960000
     *          вчера, 14:06  -> 1557745560000
     * @param d dateToMillis in string format
     * @return dateToMillis in Long format
     * @throws ParseException
     */
    public Long dateToMillisRus(String d, TimeZone timeZone) {
        Long time = 0L;
        dfs.setShortMonths(shortMonths);
        if (d.contains("сегодня")) {
            time = datePeriod(d, 0, timeZone);
        } else if (d.contains("вчера")) {
            time = datePeriod(d, -1, timeZone);
        } else {
            SimpleDateFormat parser = new SimpleDateFormat("dd MMM yy, HH:mm", LOCALE);
            parser.setTimeZone(timeZone);
            parser.setDateFormatSymbols(dfs);
            try {
                time = parser.parse(d).getTime();
            } catch (ParseException e) {
                LOG.error(e.getMessage(), e);
            }

        }
        return time;
    }

}
