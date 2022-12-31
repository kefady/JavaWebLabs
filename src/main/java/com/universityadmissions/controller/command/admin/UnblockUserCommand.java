package com.universityadmissions.controller.command.admin;

import com.universityadmissions.controller.Command;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;
import com.universityadmissions.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UnblockUserCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            UserService userService = ServiceFactory.getUserService();

            if (Objects.equals(request.getSession().getAttribute("role"), "ADMIN")) {
                int userId = Integer.parseInt(request.getParameter("userId"));
                userService.setBlockStatus(userId, false);
            }

            ConsoleCommand consoleCommand = new ConsoleCommand();
            consoleCommand.execute(request, response);
        } catch (ServiceException e) {
            Logger.getLogger(UnblockUserCommand.class.getName()).log(Level.WARNING, "Failed to unblock user.", e);
        }
    }
}
