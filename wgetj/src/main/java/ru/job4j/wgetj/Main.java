package ru.job4j.wgetj;

import java.net.URL;

/**
 * Easy java version of the wget
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $id$
 * @since 0.1
 */

public class Main {
    private static final boolean DEBUG = true;

    public static void main(String[] args) {
        Parameters param;

        try {
            if (DEBUG) {
                param = new Parameters(
                        //new URL("https://github.com/ik87/TheatreSquare/archive/master.zip"),
                        new URL("https://avatars.mds.yandex.net/get-pdb/231404/7723056e-1a65-4325-b324-71a46289ef78/s1200"),
                        1000
                );
            } else {
                param = getParameters(args);
            }
            Thread thread = new Thread(new Downloader(param));
            thread.start();
            thread.join();
        } catch (Exception e) {
            System.out.println("set correct args: wgetj [url] <speed>");
        }


    }

    private static Parameters getParameters(String[] args) throws Exception {
        URL url = new URL(args[0]);
        int maxSpeed = 10_000;
        if (args.length == 2) {
            maxSpeed = Integer.parseInt(args[1]);
        }
        return new Parameters(url, maxSpeed);
    }

}

