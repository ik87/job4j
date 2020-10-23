package ru.job4j.pooh_jms;

import java.io.BufferedWriter;
import java.util.Map;

public interface RequestResponseHandler {
    void doGet(Map<String, String> in, BufferedWriter out);
    void doPost(Map<String, String> in, BufferedWriter out);
}
