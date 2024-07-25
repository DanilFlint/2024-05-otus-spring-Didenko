package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.domain.AnswerResult;
import ru.otus.hw.domain.PositiveAnswerResult;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.NegativeAnswerResult;
import ru.otus.hw.exceptions.IncorrectAnswerException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class TestCommandProcessorImpl implements TestCommandProcessor {

    private final IOService ioService;

    @Override
    public void printPreamble() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
    }

    @Override
    public void processQuestions(List<Question> questions) {
        try {
            questions.forEach(this::processQuestion);
        } catch (InputMismatchException e) {
            ioService.printLine("Mismatch by inter number");
        } catch (IncorrectAnswerException e) {
            ioService.printLine(e.getMessage());
        }
    }

    @Override
    public void processQuestion(Question question) {
        showQuestionText(question, ioService);
        printAnswers(question.answers(), ioService);
    }

    @Override
    public int processUserAnswer(IOService ioService) {
        return ioService.readInt();
    }

    @Override
    public void showQuestionText(Question question, IOService ioService) {
        ioService.printLine(question.text());
    }

    @Override
    public void printAnswers(List<Answer> answers, IOService ioService) {
        ioService.printLine("Variants:");
        answers.forEach(withCounter((i, answer) -> {
            ioService.printFormattedLine("%d. %s", ++i, answer.text());
        }));
        ioService.printLine("");
    }

    @Override
    public void processResult(boolean isCorrect, IOService ioService) {
        AnswerResult answerResult;
        if (isCorrect) {
            answerResult = new PositiveAnswerResult();
        } else {
            answerResult = new NegativeAnswerResult();
        }

        ioService.printFormattedLine("%s\n",answerResult.getMessage());
    }

    public static <T> Consumer<T> withCounter(BiConsumer<Integer, T> consumer) {
        AtomicInteger counter = new AtomicInteger(0);
        return item -> consumer.accept(counter.getAndIncrement(), item);
    }
}
