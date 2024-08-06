package ru.otus.hw.exceptions;

public class IncorrectAnswerException extends RuntimeException {
    public IncorrectAnswerException(String message, Throwable ex) {
        super(message, ex);
    }

    public IncorrectAnswerException(String message) {
        super(message);
    }
}
