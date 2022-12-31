import com.universityadmissions.entity.User;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;
import com.universityadmissions.service.UserService;
import com.universityadmissions.util.HashPassword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

public class UserDaoTest {
    private static final String USERNAME = "kefady";
    private static final String EMAIL = "notexist@gmail.com";
    private static final Integer ID = 5;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = ServiceFactory.getUserService();
    }

    @Test
    public void isUsernameExist() {
        try {
            Assertions.assertTrue(userService.isUsernameExist(USERNAME));
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void isEmailExist() {
        try {
            Assertions.assertFalse(userService.isEmailExist(EMAIL));
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findAllUsers() {
        try {
            List<User> users = userService.findAllUsers();
            Assertions.assertEquals(5, users.size());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createUser() {
        try {
            int amountOfUsersBefore = userService.findAllUsers().size();
            User user = new User();
            user.setSurname("Тест");
            user.setName("Тест");
            user.setPatronymic("Тест");
            user.setUsername("test");
            user.setPassword(HashPassword.hashPassword("test"));
            user.setEmail("test@test.com");
            user.setCity("Тест");
            user.setRegion("Тест");
            user.setEduName("Тест");
            user.setBirthday(Date.valueOf("2003-05-27"));
            userService.addNewUser(user);
            int amountOfUsersAfter = userService.findAllUsers().size();
            Assertions.assertEquals(amountOfUsersBefore, amountOfUsersAfter); // because this user is existed
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void blockUser() {
        try {
            userService.setBlockStatus(ID, true);
            Assertions.assertTrue(userService.findUserById(ID).isBlocked());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
