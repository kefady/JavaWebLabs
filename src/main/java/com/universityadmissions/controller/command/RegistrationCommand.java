package com.universityadmissions.controller.command;

import com.universityadmissions.controller.Command;
import com.universityadmissions.entity.User;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;
import com.universityadmissions.service.UserService;
import com.universityadmissions.util.HashPassword;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class RegistrationCommand implements Command {
    private static final Logger logger = Logger.getLogger(RegistrationCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            UserService userService = ServiceFactory.getUserService();

            String surname = request.getParameter("surname");
            String name = request.getParameter("name");
            String patronymic = request.getParameter("patronymic");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String city = request.getParameter("city");
            String region = request.getParameter("region");
            String eduName = request.getParameter("eduName");
            Date birthday = Date.valueOf(request.getParameter("birthday"));

            User user = new User();
            user.setSurname(surname);
            user.setName(name);
            user.setPatronymic(patronymic);
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(HashPassword.hashPassword(password));
            user.setCity(city);
            user.setRegion(region);
            user.setEduName(eduName);
            user.setBirthday(birthday);

            Map<String, String> errors = userService.addNewUser(user);

            request.getSession().setAttribute("surname", surname);
            request.getSession().setAttribute("name", name);
            request.getSession().setAttribute("patronymic", patronymic);
            request.getSession().setAttribute("username", username);
            request.getSession().setAttribute("email", email);
            request.getSession().setAttribute("city", city);
            request.getSession().setAttribute("region", region);
            request.getSession().setAttribute("eduName", eduName);
            request.getSession().setAttribute("birthday", birthday);

            if (errors.isEmpty()) {
                response.sendRedirect("login");
                logger.info("User '" + request.getSession().getAttribute("username") + "' is successfully registered.");
            } else {
                for (HashMap.Entry<String, String> error : errors.entrySet()) {
                    request.setAttribute(error.getKey(), error.getValue());
                }
                request.getRequestDispatcher("registration").forward(request, response);
                logger.warn("Failed to registration user. Wrong data.");
            }
        } catch (ServiceException | IOException | ServletException e) {
            logger.error("Failed to registration user. UserService not work.", e);
        }
    }
}
