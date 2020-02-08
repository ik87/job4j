package ru.job4j.thread_interrupt;


/**
 * Demonstrate the interrupt and the loading animation
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 1.0
 * @since 08.02.2020
 */
public class ConsoleProgress implements Runnable {
    private char[] load = {'-', '\\', '|', '/'};

    @Override
    public void run() {
        int i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.printf("\rLoad %s", load[i++ % 4]);
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("start");
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
        progress.join();
        System.out.println("\rfinish");
    }

}
