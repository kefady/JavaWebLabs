import com.universityadmissions.entity.Exam;
import com.universityadmissions.service.ExamGradeService;
import com.universityadmissions.service.ExamService;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ExamGradeDaoTest {
    private ExamGradeService examGradeService;

    @BeforeEach
    public void setUp() {
        examGradeService = ServiceFactory.getExamGradeService();
    }

    @Test
    public void isUserExamGradeExist() {
        try {
            int userId = 2;
            int examNameId = 1;
            boolean result = examGradeService.isUserExamGradeExist(userId, examNameId);
            Assertions.assertTrue(result);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
