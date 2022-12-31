import com.universityadmissions.util.HashPassword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HashPasswordTest {
    private static final String PASSWORD = "petrenko";
    private static final String HASH = "$2a$10$Gwue01ypusSEknG4dNyN8.QEX0XUOIv0J4Bm32V.v2Co1ChRoJCgG";

    @Test
    public void hashPassword() {
        boolean result = HashPassword.checkPassword(PASSWORD, HASH);
        Assertions.assertTrue(result);
    }
}
