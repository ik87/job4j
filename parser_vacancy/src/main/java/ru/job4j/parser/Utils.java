package ru.job4j.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.parser.parsers.ParserSqlRu;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Utils, some classes for work with date, load property
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class Utils {
    private static final Logger LOG = LogManager.getLogger(Utils.class.getName());
    private String[] shortMonths = {
            "янв", "фев", "мар", "апр", "май", "июн",
            "июл", "авг", "сен", "окт", "ноя", "дек"};
    private DateFormatSymbols dfs = DateFormatSymbols.getInstance(LOCALE);
    private static final Locale LOCALE = new Locale("ru");


    /**
     * Convert parsed dateToMillis to Long
     * for example:
     * Сегодня 14:06  -> 1557831960000
     * Вчера 14:06  -> 1557745560000
     *
     * @param date dateToMillis in string format
     * @return date in Long format
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

    /**
     * General class for working with date
     * @param date any date
     * @param timeZone any timezone
     * @param template any template
     * @return date in Long format
     */
    public Long dateToMillis(String date, TimeZone timeZone, String template) {
        Long time = 0L;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(template, LOCALE);
        dfs.setShortMonths(shortMonths);
        simpleDateFormat.setDateFormatSymbols(dfs);
        simpleDateFormat.setTimeZone(timeZone);
        try {
            time = simpleDateFormat.parse(date).getTime();
        } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
        }
        return time;
    }

    /**
     * Helpers method for processing date in short word view
     * @param d
     * @param amount amount, number from this date. Where 0 means Today, -1 Yesterday
     * @param timeZone timezone
     * @return date in Long format
     */
    protected Long datePeriod(String d, int amount, TimeZone timeZone) {
        Calendar cal = new GregorianCalendar(timeZone);
        cal.add(Calendar.DATE, amount);
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(d.subSequence(d.length() - 5, d.length() - 3).toString()));
        cal.set(Calendar.MINUTE, Integer.parseInt(d.subSequence(d.length() - 2, d.length()).toString()));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }



    /**
     * Get properties from resources  and decoding cyrillic
     *
     * @return Properties
     * @throws IOException if file not found
     */
    public Properties config() throws IOException {
        Properties config;
        try (InputStream in = ParserSqlRu.class.getClassLoader().getResourceAsStream("app.properties");
             InputStreamReader inEnc = new InputStreamReader(in, "UTF-8")) {
            config = new Properties();
            config.load(inEnc);
        }
        return config;
    }
}
