package ru.job4j.pingpong;

import javafx.scene.shape.Rectangle;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $id$
 * @since 0.1
 */
public class RectangleMove implements Runnable {
    private final Rectangle rect;
    private double deltaX = 15;

    public RectangleMove(Rectangle rect) {
        this.rect = rect;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {

            if (PingPong.LIMIT_X >= (this.rect.getX() + this.rect.getWidth())) {
                deltaX *= -1;
            }
            if (0 <= this.rect.getX()) {
                deltaX *= -1;
            }
            this.rect.setX(this.rect.getX() + deltaX);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}