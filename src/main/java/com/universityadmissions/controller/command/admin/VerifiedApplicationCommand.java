package com.universityadmissions.controller.command.admin;

import com.universityadmissions.controller.Command;
import com.universityadmissions.service.ApplicationService;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.logging.Level;
import java.util.logging.Logger;

public class VerifiedApplicationCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            ApplicationService applicationService = ServiceFactory.getApplicationService();

            int applicationId = Integer.parseInt(request.getParameter("applicationId"));

            boolean isVerifiedSet = applicationService.setVerify(applicationId, true);

            if (!isVerifiedSet) {
                request.setAttribute("APPLICATION_VERIFY_ERROR", "Не вдалося верифікувати заявку.");
            }

            ConsoleCommand consoleCommand = new ConsoleCommand();
            consoleCommand.execute(request, response);
        } catch (ServiceException e) {
            Logger.getLogger(VerifiedApplicationCommand.class.getName()).log(Level.WARNING, "Failed to verified application.", e);
        }
    }
}
