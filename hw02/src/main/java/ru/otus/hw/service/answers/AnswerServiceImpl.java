package ru.otus.hw.service.answers;

import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.IncorrectAnswerException;
import ru.otus.hw.service.IOService;
import ru.otus.hw.utils.Utils;

import java.util.List;

public class AnswerServiceImpl implements AnswerService {

    private final IOService ioService;

    public AnswerServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public String processUserAnswer(IOService ioService) {
        return ioService.readStringWithPrompt("Choose your answer: ");
    }

    @Override
    public boolean processAnswer(Question question) {
        var userAnswer = processUserAnswer(ioService);
        try {
            return question.answers().get(Utils.convertAnswerToInt(userAnswer) - 1).isCorrect();
        } catch (IndexOutOfBoundsException e) {
            throw new IncorrectAnswerException("Answer do not exist");
        }
    }

    @Override
    public void printAnswers(List<Answer> answers, IOService ioService) {
        ioService.printLine("Variants:");
        answers.forEach(Utils.withCounter((i, answer) -> {
            ioService.printFormattedLine("%d. %s", ++i, answer.text());
        }));
        ioService.printLine("");
    }

}
