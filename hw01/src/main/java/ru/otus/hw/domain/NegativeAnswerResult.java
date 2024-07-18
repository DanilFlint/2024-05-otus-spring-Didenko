package ru.otus.hw.domain;

public class NegativeAnswerResult implements AnswerResult {
    @Override
    public String getMessage() {
        return "You are wrong...";
    }
}
