package ru.kazimir.bortnik.springbootmodule.repository.exceptions;

public class ItemRepositoryException extends RuntimeException {

    public ItemRepositoryException(Throwable throwable) {
        super(throwable);
    }
}

