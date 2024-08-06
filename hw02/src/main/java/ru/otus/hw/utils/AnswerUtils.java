package ru.otus.hw.utils;

import ru.otus.hw.domain.Answer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class AnswerUtils {

    public static <T> Consumer<T> withCounter(BiConsumer<Integer, T> consumer) {
        AtomicInteger counter = new AtomicInteger(0);
        return item -> consumer.accept(counter.getAndIncrement(), item);
    }

    public static boolean areThereAnswers (List<Answer> answers) {
        return !answers.isEmpty();
    }

    public static int convertAnswerToInt (String answer) {
        try {
            return Integer.parseInt(answer);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
