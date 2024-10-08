package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestCommandProcessor testCommandProcessor;

    private final QuestionDao csvQuestionDAO;

    @Override
    public void executeTest() {
        List<Question> questions = csvQuestionDAO.findAll();

        testCommandProcessor.printPreamble();
        testCommandProcessor.processQuestions(questions);

    }
}
