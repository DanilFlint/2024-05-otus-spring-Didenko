package ru.otus.hw.service;

import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

public interface TestCommandProcessor {

    public void printPreamble();

    public void processQuestions(List<Question> questions);

    public void processQuestion(Question question);

    public int processUserAnswer(IOService ioService);

    public void showQuestionText(Question question, IOService ioService);

    public void printAnswers(List<Answer> answers, IOService ioService);

    public void processResult(boolean isCorrect, IOService ioService);
}
