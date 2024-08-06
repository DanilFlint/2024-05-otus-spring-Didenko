import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.TestServiceImpl;
import ru.otus.hw.service.answers.AnswerService;
import ru.otus.hw.service.questions.QuestionService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestServiceImplTest {

    @Mock
    private IOService ioService;

    @Mock
    private QuestionDao questionDao;

    @Mock
    private AnswerService answerService;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private TestServiceImpl testService;

    @Test
    public void shouldCorrectExecuteTest() {
        Student student = new Student("Danil", "Didenko");

        when(questionDao.findAll()).thenReturn(getExpectedListQuestions());
        when(answerService.processAnswer(getExpectedListQuestions().get(0))).thenReturn(true);
        when(answerService.processAnswer(getExpectedListQuestions().get(1))).thenReturn(true);

        TestResult testResult = new TestResult(student);
        testResult.setRightAnswersCount(2);

        assertEquals(
                testResult.getRightAnswersCount(),
                testService.executeTestFor(student).getRightAnswersCount()
        );
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
