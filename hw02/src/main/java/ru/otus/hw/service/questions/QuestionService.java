package ru.otus.hw.service.questions;

import ru.otus.hw.domain.Question;
import ru.otus.hw.service.IOService;

public interface QuestionService {

    public void processQuestion(Question question);

    public void showQuestionText(Question question, IOService ioService);

}
