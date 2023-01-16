package com.universityadmissions.controller.command;

import com.universityadmissions.controller.Command;
import com.universityadmissions.entity.Application;
import com.universityadmissions.service.ApplicationService;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class ProfileCommand implements Command {
    private static final Logger logger = Logger.getLogger(ProfileCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        ApplicationService applicationService = ServiceFactory.getApplicationService();
        try {
            List<Application> applications = applicationService.findAllApplicationsByUserId((Integer) request.getSession().getAttribute("userId"));
            request.setAttribute("applications", applications);
            request.getRequestDispatcher("profile").forward(request, response);
            logger.info("User '" + request.getSession().getAttribute("username") + "' opened profile.");
        } catch (IOException | ServiceException | ServletException e) {
            logger.error("Failed to open user profile.", e);
        }
    }
}
