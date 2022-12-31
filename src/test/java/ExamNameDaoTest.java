import com.universityadmissions.service.ExamNameService;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExamNameDaoTest {
    private ExamNameService examNameService;

    @BeforeEach
    public void setUp() {
        examNameService = ServiceFactory.getExamNameService();
    }

    @Test
    public void findExamNameById() {
        try {
            int examNameId = 1;
            String expected = "Українська мова і література";
            String actual = examNameService.findExamNameById(examNameId).getName();
            Assertions.assertEquals(expected, actual);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
