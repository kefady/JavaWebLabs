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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            if ((Boolean) request.getSession().getAttribute("isLogin")) {
                response.sendRedirect("/admission/");
            } else {
                UserService userService = ServiceFactory.getUserService();
                Map<String,String> errors = new HashMap<>();

                String username = request.getParameter("username");
                String password = request.getParameter("password");
                request.getSession().setAttribute("username", username);

                if (userService.isUsernameExist(username)) {
                    User user = userService.findUserByUsername(username);
                    if (!user.isBlocked()) {
                        if(HashPassword.checkPassword(password, user.getPassword())) {
                            request.getSession().setAttribute("isLogin", true);
                            request.getSession().setAttribute("userId", user.getId());
                            request.getSession().setAttribute("surname", user.getSurname());
                            request.getSession().setAttribute("name", user.getName());
                            request.getSession().setAttribute("patronymic", user.getPatronymic());
                            request.getSession().setAttribute("email", user.getEmail());
                            request.getSession().setAttribute("eduName", user.getEduName());
                            request.getSession().setAttribute("birthday", user.getBirthday());
                            request.getSession().setAttribute("role", user.getRole().getName().toUpperCase());
                        } else {
                            errors.put("INVALID_PASSWORD", "Ви ввели невірний пароль.");
                        }
                    } else {
                        errors.put("USER_BLOCKED", "Ваш аккаунт було заблоковано.");
                    }
                } else {
                    errors.put("INVALID_LOGIN", "Ви ввели невірний логін.");
                }

                if(errors.isEmpty()) {
                    response.sendRedirect("/admission/");
                } else {
                    for(HashMap.Entry<String, String> error : errors.entrySet()) {
                        request.setAttribute(error.getKey(), error.getValue());
                    }
                    request.getRequestDispatcher("login").forward(request, response);
                }
            }
        } catch (IOException | ServiceException | ServletException e) {
            Logger.getLogger(LoginCommand.class.getName()).log(Level.WARNING, "Failed to login user.", e);
        }
    }
}
