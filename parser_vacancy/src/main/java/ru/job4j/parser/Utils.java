package ru.job4j.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {
    private static final Logger LOG = LogManager.getLogger(Utils.class.getName());

    public Long dateToMillis(String date, TimeZone timeZone) {
        //04 11 19, 10:00
        SimpleDateFormat parser = new SimpleDateFormat("dd MM yy, HH:mm");
        parser.setTimeZone(timeZone);
        Long time;
        try {
            time = parser.parse(date).getTime();
            return time;
        } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
        }
        return -1L;
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
