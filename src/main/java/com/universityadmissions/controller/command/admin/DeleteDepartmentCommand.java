package com.universityadmissions.controller.command.admin;

import com.universityadmissions.controller.Command;
import com.universityadmissions.service.DepartmentService;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class DeleteDepartmentCommand implements Command {
    private static final Logger logger = Logger.getLogger(DeleteDepartmentCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            DepartmentService departmentService = ServiceFactory.getDepartmentService();

            int departmentId = Integer.parseInt(request.getParameter("departmentId"));

            boolean isDepartmentDelete;
            isDepartmentDelete = departmentService.deleteDepartmentById(departmentId);
            if (!isDepartmentDelete) {
                request.setAttribute("FAILED_DEPARTMENT_DELETE", "Не вдалося видалити факультет.");
                logger.error("Error deleting department with id: " + departmentId);
            } else {
                logger.info("Department with id: " + departmentId + " has been deleted.");
            }
            ConsoleCommand consoleCommand = new ConsoleCommand();
            consoleCommand.execute(request, response);
        } catch (ServiceException e) {
            logger.error("Error deleting department: " + e);
        }
    }
}
