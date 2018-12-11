package ru.job4j.tracker.menu;

import ru.job4j.tracker.Item;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Show {
    /**
     * Show items by array
     *
     * @param title title name
     * @param items array of items
     */
    public static void show(String title, Item[] items) {
        System.out.println(title);
        System.out.printf("%-20s%-11s%-25s%n", "Id", "Name", "Description");
        System.out.println("------------------------------------------------------------------------------");
        for (Item item : items) {
            System.out.printf("%-20s%-11s%-25s%n",
                    item.getId(), item.getName(), item.getDesc());
        }
        System.out.println("------------------------------------------------------------------------------");
    }

    /**
     * Convert long to date
     *
     * @param date date
     * @return formated date
     */
    private static String longToDate(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd,MM,yy");
        return sdf.format(new Date(date));
    }

}
