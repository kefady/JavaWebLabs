package com.universityadmissions.controller.command;

import com.universityadmissions.controller.Command;
import com.universityadmissions.entity.Department;
import com.universityadmissions.entity.ExamName;
import com.universityadmissions.service.DepartmentService;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;
import com.universityadmissions.service.ExamNameService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplyCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            if ((Boolean) request.getSession().getAttribute("isLogin")) {
                DepartmentService departmentService = ServiceFactory.getDepartmentService();
                ExamNameService examNameService = ServiceFactory.getExamNameService();

                int departmentId = Integer.parseInt(request.getParameter("departmentId"));

                if (!String.valueOf(request.getSession().getAttribute("listOfApplyDepartment")).contains(String.valueOf(departmentId))) {
                    Department department = departmentService.findDepartmentById(departmentId);
                    List<ExamName> examNames = examNameService.findAllExamNames();
                    request.setAttribute("department", department);
                    request.setAttribute("examNames", examNames);
                    request.getRequestDispatcher("apply").forward(request, response);
                } else {
                    response.sendRedirect("/admission/");
                }
            } else {
                response.sendRedirect("login");
            }
        } catch (NumberFormatException | ServiceException | IOException | ServletException e) {
            Logger.getLogger(ApplyCommand.class.getName()).log(Level.WARNING, "Failed to open apply form.", e);
        }
    }
}
