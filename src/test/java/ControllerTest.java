import com.universityadmissions.controller.Command;
import com.universityadmissions.controller.CommandEnum;
import com.universityadmissions.controller.command.LoginCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ControllerTest {
    @Test
    public void loginCommand() {
        String command = "LOGIN";
        Command loginCommand = CommandEnum.valueOf(command).getCommand();
        Assertions.assertEquals(LoginCommand.class, loginCommand.getClass());
    }
}
