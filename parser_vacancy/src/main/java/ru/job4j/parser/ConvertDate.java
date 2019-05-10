package ru.job4j.parser;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConvertDate {
    private String[] shortMonths = {
            "янв", "фев", "мар", "апр", "май", "июн",
            "июл", "авг", "сен", "окт", "ноя", "дек"};
    private DateFormatSymbols dfs = DateFormatSymbols.getInstance(LOCALE);
    private static final Locale LOCALE = new Locale("ru");
    private TimeZone timeZone = TimeZone.getTimeZone("Europe/Moscow");

    public Long convert(String d) throws ParseException {
        //29 апр 19, 14:06
        //сегодня, 22:42
        //вчера, 11:50
        Long time = 0L;
        dfs.setShortMonths(shortMonths);
        if (d.contains("сегодня")) {
            time = datePeriod(d, 0);
        } else if (d.contains("вчера")) {
            time = datePeriod(d, -1);
        } else {
            SimpleDateFormat parser = new SimpleDateFormat("dd MMM yy, HH:mm", LOCALE);
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

    public Long currentTime() {
       Calendar cal = new GregorianCalendar(timeZone);
       cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }
}
