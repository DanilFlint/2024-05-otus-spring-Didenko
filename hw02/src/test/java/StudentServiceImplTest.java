import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.domain.Student;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.StudentServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {
    @Mock
    IOService ioService;

    @InjectMocks
    StudentServiceImpl studentService;

    @Test
    public void shouldCorrectDetermineStudent() {
        Mockito.when(ioService.readStringWithPrompt("Please input your first name")).thenReturn("Danil");
        Mockito.when(ioService.readStringWithPrompt("Please input your last name")).thenReturn("Didenko");

        Student student = new Student("Danil", "Didenko");

        assertEquals(student, studentService.determineCurrentStudent());
    }

}
