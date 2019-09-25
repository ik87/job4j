package ru.job4j.wgetj;

import org.apache.tika.Tika;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;

import java.io.*;
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
            HttpURLConnection conn = (HttpURLConnection) param.url.openConnection();
            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.out.println("No file to download. Server replied HTTP code:" + responseCode);
                return;
            }
            String fileName = "";
            String disposition = conn.getHeaderField("Content-Disposition");

            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 9);
                }
            } else {
                fileName = param.url.getFile();
                String type = new Tika().detect(param.url);
                MimeTypes allType = MimeTypes.getDefaultMimeTypes();
                MimeType extension = null;
                try {
                    extension = allType.forName(type);
                } catch (MimeTypeException e) {

                }
                if (fileName.contains("/")) {
                    String substr[] = fileName.split("/");
                    fileName = substr[substr.length - 1] + extension.getExtension();


                }

            }
            try (InputStream inputStream = conn.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(fileName)) {
                int bytesRead;
                int sum = 0;
                int readed = 0;
                long time = System.currentTimeMillis();
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    if (Thread.currentThread().isInterrupted()) {
                        break;
                    }
                    sum += bytesRead;
                    readed += bytesRead;
                    outputStream.write(buffer, 0, bytesRead);
                    if (sum >= param.maxSpeed) {
                        if ((System.currentTimeMillis() - time) < 1000L) {
                            try {
                                Thread.sleep(1000L - (System.currentTimeMillis() - time));
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }

                        }
                        System.out.printf("%s %d kb \r", fileName, readed / 1000);
                        sum = 0;
                        time = System.currentTimeMillis();


                    }
                }

                System.out.printf("%s %d kb finish", fileName, readed / 1000);

            } catch (IOException e) {
                throw e;
            } finally {
                conn.disconnect();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
