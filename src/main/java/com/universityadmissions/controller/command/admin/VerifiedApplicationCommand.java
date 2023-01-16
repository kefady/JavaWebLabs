package com.universityadmissions.controller.command.admin;

import com.universityadmissions.controller.Command;
import com.universityadmissions.service.ApplicationService;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class VerifiedApplicationCommand implements Command {
    private static final Logger logger = Logger.getLogger(VerifiedApplicationCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            ApplicationService applicationService = ServiceFactory.getApplicationService();

            int applicationId = Integer.parseInt(request.getParameter("applicationId"));

            boolean isVerifiedSet = applicationService.setVerify(applicationId, true);

            if (!isVerifiedSet) {
                request.setAttribute("APPLICATION_VERIFY_ERROR", "Не вдалося верифікувати заявку.");
                logger.error("Failed to verify application with id: " + applicationId);
            } else {
                logger.info("Application with id: " + applicationId + " was verified successfully.");
            }

            ConsoleCommand consoleCommand = new ConsoleCommand();
            consoleCommand.execute(request, response);
        } catch (ServiceException e) {
            logger.error("Error while trying to verify application: " + e);
        }
    }
}
