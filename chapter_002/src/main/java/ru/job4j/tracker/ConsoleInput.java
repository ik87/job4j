package ru.job4j.tracker;

import java.util.Scanner;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class ConsoleInput implements Input {

    private Scanner in = new Scanner(System.in);

    @Override
    public String ask(String question) {
        System.out.println(question);
        return in.nextLine();
    }

    @Override
    public int ask(String question, int[] range) throws MenuOutException {
        int key = Integer.valueOf(this.ask(question));
        boolean exist = false;
        for (int value : range) {
            if (value == key) {
                exist = true;
                break;
            }
        }
        if (exist) {
            return key;
        } else {
            throw new MenuOutException("Out of range");
        }
    }

}
