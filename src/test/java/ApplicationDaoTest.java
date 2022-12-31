import com.universityadmissions.entity.Application;
import com.universityadmissions.service.ApplicationService;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ApplicationDaoTest {
    private ApplicationService applicationService;

    @BeforeEach
    public void setUp() {
        applicationService = ServiceFactory.getApplicationService();
    }

    @Test
    public void getAmountOfUserApplications() {
        try {
            int userId = 2;
            int amountOfUserApplication = applicationService.getAmountOfUserApplications(userId);
            Assertions.assertEquals(4, amountOfUserApplication);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addFinalGrade() {
        try {
            int applicationId = 1;
            int finalGrade = 195;
            applicationService.addFinalGrade(applicationId, finalGrade);
            int actualFinalGrade = applicationService.findApplicationById(applicationId).getFinalGrade();
            Assertions.assertEquals(finalGrade, actualFinalGrade);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findAllApplicationsByUserId() {
        try {
            int userId = 1;
            List<Application> applications = applicationService.findAllApplicationsByUserId(userId);
            Assertions.assertEquals(0, applications.size());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
