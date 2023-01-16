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
import java.util.ArrayList;
import java.util.List;

public class DeleteUserApplicationCommand implements Command {
    private static final Logger logger = Logger.getLogger(DeleteUserApplicationCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        ApplicationService applicationService = ServiceFactory.getApplicationService();
        try {
            if ((Boolean) request.getSession().getAttribute("isLogin")) {
                ArrayList<Integer> listOfApplyDepartment = (ArrayList<Integer>) request.getSession().getAttribute("listOfApplyDepartment");
                int countOfUserApplications = (Integer) request.getSession().getAttribute("countOfUserApplications");
                int applicationId = Integer.parseInt(request.getParameter("applicationId"));
                int departmentId = Integer.parseInt(request.getParameter("departmentId"));

                if (applicationService.deleteApplicationById(applicationId)) {
                    listOfApplyDepartment.remove(Integer.valueOf(departmentId));
                    request.getSession().setAttribute("listOfApplyDepartment", listOfApplyDepartment);
                    countOfUserApplications--;
                    request.getSession().setAttribute("countOfUserApplications", countOfUserApplications);
                    List<Application> applications = applicationService.findAllApplicationsByUserId((Integer) request.getSession().getAttribute("userId"));
                    request.getSession().setAttribute("applications", applications);
                    request.getRequestDispatcher("profile").forward(request, response);
                    logger.info("Application with id '" + applicationId + "' has been successfully deleted.");
                } else {
                    logger.warn("Failed to application with id '" + applicationId + "'.");
                }
            } else {
                response.sendRedirect("login");
            }
        } catch (IOException | ServiceException | ServletException e) {
            logger.error("Failed to delete user application.", e);
        }
    }
}
