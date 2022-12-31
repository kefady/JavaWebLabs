package com.universityadmissions.controller.command.admin;

import com.universityadmissions.controller.Command;
import com.universityadmissions.service.DepartmentService;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteDepartmentCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            DepartmentService departmentService = ServiceFactory.getDepartmentService();

            int departmentId = Integer.parseInt(request.getParameter("departmentId"));

            boolean isDepartmentDelete;
            isDepartmentDelete = departmentService.deleteDepartmentById(departmentId);
            if (!isDepartmentDelete) {
                request.setAttribute("FAILED_DEPARTMENT_DELETE", "Не вдалося видалити факультет.");
            }

            ConsoleCommand consoleCommand = new ConsoleCommand();
            consoleCommand.execute(request, response);
        } catch (ServiceException e) {
            Logger.getLogger(DeleteDepartmentCommand.class.getName()).log(Level.WARNING, "Failed to delete department.", e);
        }
    }
}
