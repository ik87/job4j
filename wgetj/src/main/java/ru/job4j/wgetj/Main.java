package ru.job4j.wgetj;

import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws Exception {
        //Parameters param = getParameters(args);
        Parameters param = new Parameters();
        param.url = new URL("https://github.com/ik87/TheatreSquare/archive/master.zip");
        param.maxSpeed = 2000;
        (new Thread(new Downloader(param))).start();

    }

    private static Parameters getParameters(String[] args) {
        Parameters param = new Parameters();
        try {
            if (args.length == 0) {
                throw new IllegalArgumentException();
            }

            if (args.length >= 1) {
                param.url = new URL(args[0]);
                param.maxSpeed = Integer.MAX_VALUE;
            }

            if (args.length == 2) {
                param.maxSpeed = Integer.parseInt(args[1]);
            }

        } catch (IllegalArgumentException | MalformedURLException e) {
            System.out.println("is not a wgetj command. " + e.getMessage());
            System.exit(0);
        }
        return param;
    }

}


class Parameters {
    URL url;
    Integer maxSpeed;
}
