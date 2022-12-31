package com.universityadmissions.servlet;

import com.universityadmissions.dao.mysql.MysqlDaoFactory;
import com.universityadmissions.entity.Application;
import com.universityadmissions.entity.Department;
import com.universityadmissions.service.ApplicationService;
import com.universityadmissions.service.DepartmentService;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "HomeServlet", value = "")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DepartmentService departmentService = ServiceFactory.getDepartmentService();
        ApplicationService applicationService = ServiceFactory.getApplicationService();
        List<Department> departments;
        try {
            departments = departmentService.findAllDepartments();
            request.setAttribute("departments", departments);
            if (request.getServletContext().getAttribute("isApplyEnd") == null) {
                request.getServletContext().setAttribute("isApplyEnd", false);
            }
            if (request.getSession().getAttribute("isLogin") == null) {
                request.getSession().setAttribute("isLogin", false);
            } else if ((Boolean) request.getSession().getAttribute("isLogin")) {
                int userId = (Integer) request.getSession().getAttribute("userId");
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
            Logger.getLogger(MysqlDaoFactory.class.getName()).log(Level.WARNING, "Home servlet error", e);
        }
    }
}
