package ru.job4j.wgetj;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Easy java version of the wget
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $id$
 * @since 0.1
 */

public class Main {
    private static final boolean DEBUG = false;

    public static void main(String[] args) throws Exception {
        Parameters param = new Parameters();
        if (DEBUG) {
            param.setUrl(new URL("https://github.com/ik87/TheatreSquare/archive/master.zip"));
            param.setMaxSpeed(200);
        } else {
            param = getParameters(args);
        }
        Thread thread = new Thread(new Downloader(param));
        thread.start();
        thread.join();

    }

    private static Parameters getParameters(String[] args) {
        Parameters param = new Parameters();
        try {
            if (args.length == 0 || args.length > 2) {
                throw new IllegalArgumentException();
            }

            if (args.length > 0) {
                param.setUrl(new URL(args[0]));
            }

            if (args.length == 2) {
                param.setMaxSpeed(Integer.parseInt(args[1]));
            }

        } catch (IllegalArgumentException | MalformedURLException e) {
            System.out.println("set correct args: wgetj [url] <speed>");
            System.exit(1);
        }
        return param;
    }

}

