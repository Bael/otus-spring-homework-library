package ru.otus.spring.hw.library.exceptions;

public class NotFoundException extends RuntimeException {


    public NotFoundException(String message) {
        super(message);

    }
}
