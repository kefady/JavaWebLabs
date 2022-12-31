package com.universityadmissions.controller.command;

import com.universityadmissions.controller.Command;
import com.universityadmissions.entity.Application;
import com.universityadmissions.service.ApplicationService;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProfileCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        ApplicationService applicationService = ServiceFactory.getApplicationService();
        try {
            List<Application> applications = applicationService.findAllApplicationsByUserId((Integer) request.getSession().getAttribute("userId"));
            request.setAttribute("applications", applications);
            request.getRequestDispatcher("profile").forward(request, response);
        } catch (IOException | ServiceException | ServletException e) {
            Logger.getLogger(ProfileCommand.class.getName()).log(Level.WARNING, "Failed to open user profile.", e);
        }
    }
}
