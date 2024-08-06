import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CsvQuestionDAOTest {

    @Mock
    private TestFileNameProvider fileNameProvider;

    @InjectMocks
    private CsvQuestionDao csvQuestionDao;

    @Test
    public void shouldCorrectReadFile() {
        when(fileNameProvider.getTestFileName()).thenReturn("questions2.csv");

        assertEquals(getExpectedListQuestions(), csvQuestionDao.findAll());
    }

    private List<Question> getExpectedListQuestions() {
        List<Question> expectedList = new ArrayList<>();

        List<Answer> answers1 = new ArrayList<>();
        answers1.add(new Answer("Dragon", false));
        answers1.add(new Answer("Rabbit", false));
        answers1.add(new Answer("Dog", false));
        answers1.add(new Answer("Hummingbird", true));

        List<Answer> answers2 = new ArrayList<>();
        answers2.add(new Answer("Venus", true));
        answers2.add(new Answer("Mercury", false));
        answers2.add(new Answer("Saturn", false));
        answers2.add(new Answer("Mars", false));

        expectedList.add(new Question("Which animal does not appear in the Chinese zodiac?", answers1));
        expectedList.add(new Question("Which planet is the hottest?", answers2));

        return expectedList;
    }
}
