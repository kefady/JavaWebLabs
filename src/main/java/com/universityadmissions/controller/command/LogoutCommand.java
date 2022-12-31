package com.universityadmissions.controller.command;

import com.universityadmissions.controller.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogoutCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if ((Boolean) session.getAttribute("isLogin")) {
            session.setAttribute("isLogin", false);
            session.invalidate();
            try {
                response.sendRedirect("/admission/");
            } catch (IOException e) {
                Logger.getLogger(LogoutCommand.class.getName()).log(Level.WARNING, "Failed to logout user.", e);
            }
        }
    }
}
