package ru.kazimir.bortnik.repository.exceptions;

public class ConnectionDataBaseExceptions extends RuntimeException {


    public ConnectionDataBaseExceptions(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ConnectionDataBaseExceptions(String message) {
        super(message);
    }
}
