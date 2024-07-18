package ru.otus.hw.domain;

public class PositiveAnswerResult implements AnswerResult {
    @Override
    public String getMessage() {
        return "You are right!";
    }
}
