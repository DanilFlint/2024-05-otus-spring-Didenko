package ru.otus.hw.service.answers;

import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.service.IOService;

import java.util.List;

public interface AnswerService {

    public String processUserAnswer(IOService ioService);

    public boolean processAnswer(Question question);

    public void printAnswers(List<Answer> answers, IOService ioService);

}
