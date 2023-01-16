package com.universityadmissions.servlet;

import com.universityadmissions.entity.Application;
import com.universityadmissions.entity.Department;
import com.universityadmissions.service.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "HomeServlet", value = "")
public class HomeServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(HomeServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DepartmentService departmentService = ServiceFactory.getDepartmentService();
        ApplicationService applicationService = ServiceFactory.getApplicationService();
        UserService userService = ServiceFactory.getUserService();
        List<Department> departments;
        try {
            departments = departmentService.findAllDepartments();
            request.setAttribute("departments", departments);
            if (request.getServletContext().getAttribute("isApplyEnd") == null) {
                request.getServletContext().setAttribute("isApplyEnd", false);
                logger.info("New user opened home page.");
            }
            if (request.getSession().getAttribute("isLogin") == null) {
                request.getSession().setAttribute("isLogin", false);
            } else if ((Boolean) request.getSession().getAttribute("isLogin")) {
                int userId = (Integer) request.getSession().getAttribute("userId");
                logger.info("User '" + userService.findUserById(userId).getUsername() + "' opened home page.");
                if (request.getSession().getAttribute("countOfUserApplications") == null) {
                    int countOfUserApplications = applicationService.getAmountOfUserApplications(userId);
                    request.getSession().setAttribute("countOfUserApplications", countOfUserApplications);
                }
                if (request.getSession().getAttribute("listOfApplyDepartment") == null) {
                    List<Application> applications = applicationService.findAllApplicationsByUserId(userId);
                    List<Integer> listOfApplyDepartment = new ArrayList<>();
                    for (Application application : applications) {
                        listOfApplyDepartment.add(application.getDepartment().getId());
                    }
                    request.getSession().setAttribute("listOfApplyDepartment", listOfApplyDepartment);
                }
            }
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (ServiceException e) {
            logger.error("Failed to load home page.", e);
        }
    }
}
