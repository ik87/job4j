package ru.job4j.patterns.singleton;

public class LoadOrder {

    private static String staticFiled = echo("static field");

    private static final String STATIC_FINAL_FIELD = echo("static final field");

    {
        echo("not-static block");
    }

    static {
        echo("static block");
    }

    private String surname = echo("field");

    private final String name = echo("final field");

    public LoadOrder(String msg) {
        echo("constructor " + msg);
    }

    public static String echo(String text) {
        System.out.println(text);
        return text;
    }

    private static final class Holder {
        private static final LoadOrder INSTANCE = new LoadOrder("static inner field");
    }

    public static void main(String[] args) {
        //LoadOrder order = new LoadOrder("main");
    }
}