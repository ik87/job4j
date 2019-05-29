package ru.job4j.parser.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.parser.Utils;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.TimeZone;

/**
 * This class used for work with time. Converting or get current time
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class UtilsAvitoRu extends Utils {
    private final static Logger LOG = LogManager.getLogger(UtilsAvitoRu.class.getName());
    private String[] months = {
            "января", "февраля", "марта", "апреля", "мая", "июня",
            "июля", "августа", "сентября", "окттября", "ноября", "декабря"};
    private static final Locale LOCALE = new Locale("ru");
    private DateFormatSymbols dfs = DateFormatSymbols.getInstance(LOCALE);
    Locale rusLocale = new Locale.Builder().setLanguage("ru").setScript("Cyrl").build();

    /**
     * Convert parsed dateToMillis to Long
     * for example:
     *          29 апрeля 14:06 -> 1556535960000
     *          Сегодня 14:06  -> 1557831960000
     *          Вчера 14:06  -> 1557745560000
     * @param d dateToMillis in string format
     * @return dateToMillis in Long format
     * @throws ParseException
     */
    public Long dateToMillisRus(String d, TimeZone timeZone) {
        Long time = 0L;
       dfs.setMonths(months);
        if (d.contains("Сегодня")) {
            time = datePeriod(d, 0, timeZone);
        } else if (d.contains("Вчера")) {
            time = datePeriod(d, -1, timeZone);
        } else {
          SimpleDateFormat parser = new SimpleDateFormat("dd MMMM HH:mm", LOCALE);
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
