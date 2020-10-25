package ru.job4j.pooh_jms;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;

public class Queue implements RequestResponseHandler {
    private Map<String, EntityQueue> buffer = new ConcurrentHashMap<>();

    @Override
    public void doGet(Map<String, String> in, BufferedWriter out) {
        try {
            try {
                String path = in.get("Path").split("/")[2];
                String msg = null;
                if(buffer.containsKey(path)) {
                    msg = new Gson().toJson(buffer.remove(path));
                }

                if (msg != null) {
                    Service.ok(out);
                    String res = "Content-type: application/json; charset=UTF-8\n"
                            + "Content-Length: " + msg.length() + "\n\n"
                            + msg;
                    out.write(res);
                    out.flush();
                    return;
                }
            } catch (IndexOutOfBoundsException e) {

            }

            Service.notFound(out);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void doPost(Map<String, String> in, BufferedWriter out) {
        String json = in.get("Body");
        try {
            if (json != null && !json.isEmpty()) {
                Pattern r = Pattern.compile("queue.*:(.*),");
                Matcher m = r.matcher(json);
                if (m.find() && m.groupCount() > 0) {
                    EntityQueue entityQueue = new Gson().fromJson(json, EntityQueue.class);
                    buffer.put(entityQueue.getQueue(), entityQueue);
                    Service.ok(out);
                    String res = "Content-type: application/json; charset=UTF-8\n"
                            + "Content-Length: " + json.length() + "\n\n"
                            + json;
                    out.write(res);
                    out.flush();
                    return;
                }
            }
            Service.badRequest(out);
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
