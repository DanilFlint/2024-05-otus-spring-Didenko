package ru.otus.hw.service.questions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Question;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.answers.AnswerService;
import ru.otus.hw.utils.AnswerUtils;

@Service
@RequiredArgsConstructor
public class QuestionCsvServiceImpl implements QuestionService {

    private final IOService ioService;

    private final AnswerService answerService;

    @Override
    public void processQuestion(Question question) {
        showQuestionText(question, ioService);
        if (AnswerUtils.areThereAnswers(question.answers())) {
            answerService.printAnswers(question.answers(), ioService);
        }
    }

    @Override
    public void showQuestionText(Question question, IOService ioService) {
        ioService.printLine(question.text());
    }
}
