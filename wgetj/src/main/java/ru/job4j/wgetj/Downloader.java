package ru.job4j.wgetj;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $id$
 * @since 0.1
 */
class Downloader implements Runnable {
    private static final int BUFFER_SIZE = 4096;
    private Parameters param;

    public Downloader(Parameters param) {
        this.param = param;
    }


    @Override
    public void run() {
        try {
            HttpURLConnection conn = (HttpURLConnection) param.getUrl().openConnection();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String fileName = "";
                String disposition = conn.getHeaderField("Content-Disposition");

                if (disposition != null) {
                    // extracts file name from header field
                    int index = disposition.indexOf("filename=");
                    if (index > 0) {
                        fileName = disposition.substring((index - 1) + 10);
                    }
                } else {
                    // extracts file name from URL
                    fileName = param.getUrl().getFile();
                }
                try (InputStream inputStream = conn.getInputStream();
                     FileOutputStream outputStream = new FileOutputStream(fileName)) {
                    int bytesRead = -1;
                    byte[] buffer = new byte[BUFFER_SIZE];
                    long sum = 0L;
                    long time = System.currentTimeMillis();
                    long maxSpeed = param.getMaxSpeed();
                    while (!Thread.currentThread().isInterrupted()) {
                        if ((bytesRead = inputStream.read(buffer)) != -1) {
                            sum += bytesRead;
                            if(param.getMaxSpeed() != 0) { //if speed didn't set then max speed
                                if ((maxSpeed -= bytesRead) <= 0) { //the speed limited gate
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                    }
                                    maxSpeed = param.getMaxSpeed();
                                }
                            }
                            //prints downloaded data every 1s
                            if((System.currentTimeMillis() - time) > 1000L) {
                                System.out.printf("%s %d kb \r", fileName, sum / 1000);
                                time = System.currentTimeMillis();
                            }
                            outputStream.write(buffer, 0, bytesRead);
                        } else {
                            Thread.currentThread().interrupt();
                        }


                    }
                    System.out.printf("%s %d kb \r", fileName, sum / 1000);
                } catch (IOException e) {
                    conn.disconnect();
                    throw e;
                }
            } else {
                System.out.println("No file to download. Server replied HTTP code:" + responseCode);
            }
            conn.disconnect();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
