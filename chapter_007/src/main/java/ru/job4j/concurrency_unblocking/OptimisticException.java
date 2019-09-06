package ru.job4j.concurrency_unblocking;

public class OptimisticException extends RuntimeException {
    public OptimisticException() {
    }

    public OptimisticException(String msg) {
        super(msg);
    }
}
