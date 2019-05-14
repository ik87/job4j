package ru.job4j.parser;

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
public class Converter {
    private String[] shortMonths = {
            "янв", "фев", "мар", "апр", "май", "июн",
            "июл", "авг", "сен", "окт", "ноя", "дек"};
    private DateFormatSymbols dfs = DateFormatSymbols.getInstance(LOCALE);
    private static final Locale LOCALE = new Locale("ru");
    private TimeZone timeZone = TimeZone.getTimeZone("Europe/Moscow");

    /**
     * Convert parsed date to Long
     * for example:
     *          29 апр 19, 14:06 -> 1556535960000
     *          сегодня, 14:06  -> 1557831960000
     *          вчера, 14:06  -> 1557745560000
     * @param d date in string format
     * @return date in Long format
     * @throws ParseException
     */
    public Long date(String d) throws ParseException {
        Long time = 0L;
        dfs.setShortMonths(shortMonths);
        if (d.contains("сегодня")) {
            time = datePeriod(d, 0);
        } else if (d.contains("вчера")) {
            time = datePeriod(d, -1);
        } else {
            SimpleDateFormat parser = new SimpleDateFormat("dd MMM yy, HH:mm", LOCALE);
            parser.setTimeZone(timeZone);
            parser.setDateFormatSymbols(dfs);
            time = parser.parse(d).getTime();

        }
        return time;
    }

    private Long datePeriod(String d, int amount) {
        Calendar cal = new GregorianCalendar(timeZone);
        cal.add(Calendar.DATE, amount);
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(d.subSequence(d.length() - 5, d.length() - 3).toString()));
        cal.set(Calendar.MINUTE, Integer.parseInt(d.subSequence(d.length() - 2, d.length()).toString()));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * Get current time depends by timezone
     * @return get current time in Long formats
     */
    public Long currentTime() {
       Calendar cal = new GregorianCalendar(timeZone);
       cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }
}
