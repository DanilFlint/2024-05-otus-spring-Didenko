package ru.otus.hw.service.answers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.IncorrectAnswerException;
import ru.otus.hw.service.IOService;
import ru.otus.hw.utils.AnswerUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final IOService ioService;

    @Override
    public String processUserAnswer(IOService ioService) {
        return ioService.readStringWithPrompt("Choose your answer: ");
    }

    @Override
    public boolean processAnswer(Question question) {
        var userAnswer = processUserAnswer(ioService);
        try {
            return question.answers().get(AnswerUtils.convertAnswerToInt(userAnswer) - 1).isCorrect();
        } catch (IndexOutOfBoundsException e) {
            throw new IncorrectAnswerException("Answer do not exist");
        }
    }

    @Override
    public void printAnswers(List<Answer> answers, IOService ioService) {
        ioService.printLine("Variants:");
        answers.forEach(AnswerUtils.withCounter((i, answer) -> {
            ioService.printFormattedLine("%d. %s", ++i, answer.text());
        }));
        ioService.printLine("");
    }

}
