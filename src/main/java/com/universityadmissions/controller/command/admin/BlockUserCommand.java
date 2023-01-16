package com.universityadmissions.controller.command.admin;

import com.universityadmissions.controller.Command;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;
import com.universityadmissions.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.util.Objects;

public class BlockUserCommand implements Command {
    private static final Logger logger = Logger.getLogger(BlockUserCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            UserService userService = ServiceFactory.getUserService();

            if (Objects.equals(request.getSession().getAttribute("role"), "ADMIN")) {
                int userId = Integer.parseInt(request.getParameter("userId"));
                if (Objects.equals(userService.findUserById(userId).getRole().getName().toUpperCase(), "USER")) {
                    userService.setBlockStatus(userId, true);
                    logger.info("User with id '" + userId + "' blocked.");
                } else {
                    request.setAttribute("USER_BLOCK_ROLE_ERROR", "Ви не можете заблокувати користувача, у якого роль 'ADMIN'.");
                }
            }

            ConsoleCommand consoleCommand = new ConsoleCommand();
            consoleCommand.execute(request, response);
        } catch (ServiceException e) {
            logger.error("Error blocking user: " + e);
        }
    }
}
