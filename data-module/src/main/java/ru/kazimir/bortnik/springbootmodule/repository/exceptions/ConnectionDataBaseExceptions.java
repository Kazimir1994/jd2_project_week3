package ru.kazimir.bortnik.springbootmodule.repository.exceptions;

public class ConnectionDataBaseExceptions extends RuntimeException {


    public ConnectionDataBaseExceptions(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ConnectionDataBaseExceptions(String message) {
        super(message);
    }
}
