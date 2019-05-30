package ru.job4j.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {
    private static final Logger LOG = LogManager.getLogger(Utils.class.getName());

    public Long dateToMillis(String date, TimeZone timeZone, String template) {
        Long time = 0L;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(template);
        simpleDateFormat.setTimeZone(timeZone);
        try {
            time = simpleDateFormat.parse(date).getTime();
        } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
        }
        return time;
    }


    protected Long datePeriod(String d, int amount, TimeZone timeZone) {
        Calendar cal = new GregorianCalendar(timeZone);
        cal.add(Calendar.DATE, amount);
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(d.subSequence(d.length() - 5, d.length() - 3).toString()));
        cal.set(Calendar.MINUTE, Integer.parseInt(d.subSequence(d.length() - 2, d.length()).toString()));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public String millisToDate(Long millis, TimeZone timeZone) {
        Date date = new Date(millis);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MM yy, HH:mm");
        formatter.setTimeZone(timeZone);
        return formatter.format(date);
    }
}
