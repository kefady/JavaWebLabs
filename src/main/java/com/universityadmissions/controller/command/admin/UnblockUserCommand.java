package com.universityadmissions.controller.command.admin;

import com.universityadmissions.controller.Command;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;
import com.universityadmissions.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.util.Objects;

public class UnblockUserCommand implements Command {
    private static final Logger logger = Logger.getLogger(UnblockUserCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            UserService userService = ServiceFactory.getUserService();

            if (Objects.equals(request.getSession().getAttribute("role"), "ADMIN")) {
                int userId = Integer.parseInt(request.getParameter("userId"));
                userService.setBlockStatus(userId, false);
                logger.info("Admin unblocked user with ID: " + userId);
            }

            ConsoleCommand consoleCommand = new ConsoleCommand();
            consoleCommand.execute(request, response);
        } catch (ServiceException e) {
            logger.error("Error unblocking user: " + e);
        }
    }
}
