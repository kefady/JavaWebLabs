package com.universityadmissions.controller.command.admin;

import com.universityadmissions.controller.Command;
import com.universityadmissions.entity.*;
import com.universityadmissions.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ConsoleCommand implements Command {
    private static final Logger logger = Logger.getLogger(ConsoleCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (Objects.equals(request.getSession().getAttribute("role"), "ADMIN")) {
                ApplicationService applicationService = ServiceFactory.getApplicationService();
                DepartmentService departmentService = ServiceFactory.getDepartmentService();
                ExamService examService = ServiceFactory.getExamService();
                ExamNameService examNameService = ServiceFactory.getExamNameService();
                UserService userService = ServiceFactory.getUserService();
                List<Application> applications = applicationService.findAllApplications();
                List<Department> departments = departmentService.findAllDepartments();
                List<Exam> exams = examService.findAllExams();
                List<ExamName> examNames = examNameService.findAllExamNames();
                List<User> users = userService.findAllUsers();
                request.setAttribute("applications", applications);
                request.setAttribute("departments", departments);
                request.setAttribute("exams", exams);
                request.setAttribute("examNames", examNames);
                request.setAttribute("users", users);
                request.getRequestDispatcher("console").forward(request, response);
                logger.info("Admin user accessed console.");
            } else {
                response.sendRedirect("login");
            }
        } catch (ServiceException | ServletException | IOException e) {
            logger.error("Error displaying console: " + e);
        }
    }
}
