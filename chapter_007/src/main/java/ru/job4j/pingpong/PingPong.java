package ru.job4j.pingpong;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * PingPong
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $id$
 * @since 0.1
 */
public class PingPong extends Application {
    private static final String JOB4J = "Пинг-понг www.job4j.ru";
    public final static int LIMIT_X = 300;
    public final static int LIMIT_Y = 300;

    @Override
    public void start(Stage stage) {
        Group group = new Group();
        Rectangle rect = new Rectangle(50, 100, 10, 10);
        group.getChildren().add(rect);
        Thread thread = new Thread(new RectangleMove(rect));
        thread.start();
        Scene scene = new Scene(group, LIMIT_X, LIMIT_Y, Color.RED);
        stage.setScene(scene);
        stage.setTitle(JOB4J);
        stage.setResizable(false);
        stage.setOnCloseRequest(windowEvent -> thread.interrupt());
        stage.show();
    }
}
