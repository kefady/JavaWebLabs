import com.universityadmissions.entity.Exam;
import com.universityadmissions.service.ExamService;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ExamDaoTest {
    private ExamService examService;

    @BeforeEach
    public void setUp() {
        examService = ServiceFactory.getExamService();
    }

    @Test
    public void findAllExams() {
        try {
            List<Exam> exams = examService.findAllExams();
            Assertions.assertEquals(23, exams.size());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
