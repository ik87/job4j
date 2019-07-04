package ru.job4j.wgetj;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class Downloader implements Runnable {
    private static final int BUFFER_SIZE = 4096;
    private Parameters parameters;

    public Downloader(Parameters parameters) {
        this.parameters = parameters;
    }

    public HttpURLConnection init(URL url) throws IOException {
        return (HttpURLConnection) parameters.url.openConnection();
    }

    private int tryGetFileSize(HttpURLConnection conn) {
        try {
            conn.setRequestMethod("HEAD");
            conn.getInputStream();
            return conn.getContentLength();
        } catch (IOException e) {
            return -1;
        } finally {
            conn.disconnect();
        }
    }

    @Override
    public void run() {
        try {
            HttpURLConnection conn = init(parameters.url);
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String fileName = "";
                String disposition = conn.getHeaderField("Content-Disposition");

                if (disposition != null) {
                    // extracts file name from header field
                    int index = disposition.indexOf("filename=");
                    if (index > 0) {
                        fileName = disposition.substring(index + 10,
                                disposition.length());
                    }
                } else {
                    // extracts file name from URL
                    fileName = parameters.url.getFile();
                }
                try (InputStream inputStream = conn.getInputStream();
                     FileOutputStream outputStream = new FileOutputStream(fileName)) {
                    int bytesRead = -1;
                    byte[] buffer = new byte[BUFFER_SIZE];
                    long maxSpeed = parameters.maxSpeed * 1000;
                    long timer = System.nanoTime();
                    long downloaded = 0;
                    Runnable printDownload = new Runnable() {
                        int downloaded;

                        public void putDownload(int downloaded) {
                            this.downloaded = downloaded;
                        }

                        @Override
                        public void run() {
                            while (!Thread.currentThread().isInterrupted()) {
                                System.out.printf(" %d \r", downloaded / 1000);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                }
                            }
                        }
                    };
                    Thread thread = new Thread(printDownload);
                    while (!Thread.currentThread().isInterrupted()) {

                        if ((bytesRead = inputStream.read(buffer)) != -1) {
                            downloaded += BUFFER_SIZE;
                            if ((maxSpeed -= BUFFER_SIZE) < 0) {

                                if ((System.nanoTime() - timer) > 100000L) {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                    }
                                }
                                maxSpeed = parameters.maxSpeed;
                                timer = System.nanoTime();
                            }
                            outputStream.write(buffer, 0, bytesRead);
                        } else {
                            Thread.currentThread().interrupt();
                        }


                    }
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
