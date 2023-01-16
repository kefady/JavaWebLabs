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
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class ApplyCommand implements Command {
    private static final Logger logger = Logger.getLogger(ApplyCommand.class);

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
                    logger.warn("User '" + request.getSession().getAttribute("username") + "' has already applied for department with id '" + departmentId + "'.");
                    response.sendRedirect("/admission/");
                }
            } else {
                logger.warn("Unauthorized user attempted to submit a apply form.");
                response.sendRedirect("login");
            }
        } catch (NumberFormatException | ServiceException | IOException | ServletException e) {
            logger.error("Failed to open apply form.", e);
        }
    }
}
