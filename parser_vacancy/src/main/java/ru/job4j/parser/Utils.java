package ru.job4j.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {
    private static final Logger LOG = LogManager.getLogger(Utils.class.getName());
    public static final TimeZone EUROPE_MOSCOW = TimeZone.getTimeZone("Europe/Moscow");


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

    public String millisToDate(Long millis, TimeZone timeZone) {
        Date date = new Date(millis);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MM yy, HH:mm");
        formatter.setTimeZone(timeZone);
        return formatter.format(date);
    }


}
