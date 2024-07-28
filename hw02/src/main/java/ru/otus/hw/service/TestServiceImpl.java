package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.service.answers.AnswerService;
import ru.otus.hw.service.answers.AnswerServiceImpl;
import ru.otus.hw.service.questions.QuestionCsvServiceImpl;
import ru.otus.hw.service.questions.QuestionService;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);
        AnswerService answerService = new AnswerServiceImpl(ioService);
        QuestionService questionService = new QuestionCsvServiceImpl(ioService, answerService);

        for (var question: questions) {
            questionService.processQuestion(question);
            var isAnswerValid = answerService.processAnswer(question);
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }
}
