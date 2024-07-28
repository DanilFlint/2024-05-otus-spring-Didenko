package ru.otus.hw.service.questions;

import ru.otus.hw.domain.Question;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.answers.AnswerService;
import ru.otus.hw.utils.Utils;

public class QuestionCsvServiceImpl implements QuestionService {

    private final IOService ioService;

    private final AnswerService answerService;

    public QuestionCsvServiceImpl(IOService ioService, AnswerService answerService) {
        this.ioService = ioService;
        this.answerService = answerService;
    }

    @Override
    public void processQuestion(Question question) {
        showQuestionText(question, ioService);
        if (Utils.areThereAnswers(question.answers())) {
            answerService.printAnswers(question.answers(), ioService);
        }
    }

    @Override
    public void showQuestionText(Question question, IOService ioService) {
        ioService.printLine(question.text());
    }
}
