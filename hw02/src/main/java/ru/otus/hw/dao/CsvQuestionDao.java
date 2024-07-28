package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        ClassLoader classLoader = getClass().getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(fileNameProvider.getTestFileName())) {
            CsvToBean<QuestionDto> beans = new CsvToBeanBuilder<QuestionDto>(new InputStreamReader(inputStream))
                    .withSeparator(';')
                    .withType(QuestionDto.class)
                    .withSkipLines(1)
                    .build();
            return beans.stream()
                    .map(questionDto -> new Question(questionDto.getText(), questionDto.getAnswers()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (QuestionReadException e) {
            throw new QuestionReadException(e.getMessage());
        }
    }
}
