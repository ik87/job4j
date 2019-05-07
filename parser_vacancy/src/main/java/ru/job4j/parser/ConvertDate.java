package ru.job4j.parser;

import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ConvertDate {
    private String[] shortMonths = {
            "янв", "фев", "мар", "апр", "май", "июн",
            "июл", "авг", "сен", "окт", "ноя", "дек"};
    private DateFormatSymbols dfs = DateFormatSymbols.getInstance(LOCALE_RU);

    private static final Locale LOCALE_RU = new Locale("ru");


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
            SimpleDateFormat parser = new SimpleDateFormat("d MMM yy, HH:mm", LOCALE_RU);
            parser.setDateFormatSymbols(dfs);
            time = parser.parse(d).getTime();
        }
        return time;
    }

    private Long datePeriod(String d, int amount) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, amount);
        cal.set(Calendar.HOUR, Integer.parseInt(d.subSequence(d.length() - 5, d.length() - 3).toString()));
        cal.set(Calendar.MINUTE, Integer.parseInt(d.subSequence(d.length() - 2, d.length()).toString()));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        System.out.println(new Timestamp(cal.getTime().getTime()));
        return cal.getTime().getTime();
    }
}
