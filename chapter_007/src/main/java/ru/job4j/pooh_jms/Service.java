package ru.job4j.pooh_jms;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service {
    private static final String SERVER = "127.0.0.1";
    private static final int PORT = 80;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(PORT);
        int numCors = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(numCors);

        boolean running = true;
        while (running) {
            Socket socket = serverSocket.accept();
            System.out.println("connected");
            pool.submit(() -> {
                Connect.accept(socket);
            });
        }
    }
}

class Connect {
    private static SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:Ss z");

    public static void accept(Socket socket) {
        // Get input and output streams
        try (BufferedReader in =
                     new BufferedReader(
                             new InputStreamReader(socket.getInputStream()));
             OutputStream out = socket.getOutputStream()) {


            String userInput;
            int postDataI = -1;

            while ((userInput = in.readLine()) != null && (userInput.length() != 0)) {
                System.out.println("echo: " + userInput);
                if (userInput.indexOf("Content-Length:") > -1) {
                    postDataI = Integer.valueOf(
                            userInput.substring(
                                    userInput.indexOf("Content-Length:") + 16,
                                    userInput.length()));
                }
            }

            String postData = "";
            // read the post data
            if (postDataI > 0) {
                char[] charArray = new char[postDataI];
                in.read(charArray, 0, postDataI);
                postData = new String(charArray);
            }
            System.out.println(postData);

            String res = "HTTP/1.0 200 OK\n"
                    + "Server: HTTP server/0.1\n"
                    + "Date: " + format.format(new java.util.Date()) + "\n"
                    + "Content-type: text/html; charset=UTF-8\n"
                    + "Content-Length: 38\n\n"
                    + "<html><body>OK</body></html>";
            out.write(res.getBytes("UTF-8"));
            out.flush();

            // Close our connection

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("close connect");
    }
}
