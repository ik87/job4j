package ru.job4j.pooh_jms;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

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
    private final static Map<String, Map<String, BiConsumer<Map<String, String>, BufferedWriter>>> METHOD;
    private final static RequestResponseHandler queue = new Queue();
    private final static RequestResponseHandler topic = new Topic();

    static {
        METHOD = new HashMap<>();
        METHOD.put("GET", Map.of("/queue", queue::doGet, "/topic", topic::doGet));
        METHOD.put("POST", Map.of("/queue", queue::doPost, "/topic",topic::doPost));
    }

    public static void accept(Socket socket) {
        // Get input and output streams
        try (BufferedReader in =
                     new BufferedReader(
                             new InputStreamReader(socket.getInputStream()));
             BufferedWriter out =
                     new BufferedWriter(
                             new OutputStreamWriter(socket.getOutputStream()))) {

            // filter
            Map<String, String> req = reqToMap(in);
            String method = req.get("Method");
            String path = req.get("Path");
            var handler = METHOD.get(method);
            boolean isOk = false;
            if("GET".equals(method)) {
                for (String key : handler.keySet()) {
                    if (path.startsWith(key)) {
                        handler.get(key).accept(req, out);
                        isOk = true;
                        break;
                    }
                }
            } else if("POST".equals(method)) {
                if(handler.containsKey(path)) {
                    handler.get(path).accept(req, out);
                    isOk = true;
                }
            }

            if(!isOk) {
                error(out);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("close connect");
    }

    static Map<String, String> reqToMap(BufferedReader in) throws IOException {
        Map<String, String> req = new LinkedHashMap<>();
        String userInput = in.readLine();
        req.put("Method", userInput.split(" ")[0]);
        req.put("Path", userInput.split(" ")[1]);
        while ((userInput = in.readLine()) != null && (userInput.length() != 0)) {
            String[] arrReq = userInput.split(":");
            req.put(arrReq[0], arrReq[1].trim());
        }
        String contentLength = req.get("Content-Length");
        if (contentLength != null && contentLength.length() != 0) {
            int postDataI = Integer.valueOf(contentLength);
            char[] charArray = new char[postDataI];
            in.read(charArray, 0, postDataI);
            req.put("Body", String.valueOf(charArray));
        }
        return req;
    }

    static void error(BufferedWriter out) throws IOException {
        String res = "HTTP/1.0 404 Not Found\n"
                + "Server: HTTP server/0.1\n"
                + "Date: " + format.format(new java.util.Date()) + "\n"
                + "Content-type: text/html; charset=UTF-8\n"
                + "Content-Length: 93\n\n"
                + "<HTML><HEAD>\n" +
                "<TITLE>404 Not Found</TITLE>\n" +
                "</HEAD><BODY>\n" +
                "<H1>Not Found</H1>\n" +
                "</BODY></HTML>";
        out.write(res);
        out.flush();
    }

}
