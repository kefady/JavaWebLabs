import com.universityadmissions.entity.Department;
import com.universityadmissions.service.DepartmentService;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepartmentDaoTest {
    private DepartmentService departmentService;

    @BeforeEach
    public void setUp() {
        departmentService = ServiceFactory.getDepartmentService();
    }

    @Test
    public void isDepartmentExist() {
        try {
            String name = "Факультет філософії";
            boolean actual = departmentService.isDepartmentExist(name);
            Assertions.assertFalse(actual);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void updateDepartment() {
        try {
            int departmentId = 2;
            int newBudgetPlaces = 1;
            int newAllPlaces = 3;
            Department department = departmentService.findDepartmentById(departmentId);
            department.setBudgetPlace(newBudgetPlaces);
            department.setAllPlace(newAllPlaces);
            departmentService.updateDepartment(department);
            Department actualDepartment = departmentService.findDepartmentById(departmentId);
            Assertions.assertEquals(newAllPlaces, actualDepartment.getAllPlace());
            Assertions.assertEquals(newBudgetPlaces, actualDepartment.getBudgetPlace());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
