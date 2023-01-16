package com.universityadmissions.controller.command;

import com.universityadmissions.controller.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;

public class LogoutCommand implements Command {
    private static final Logger logger = Logger.getLogger(LogoutCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if ((Boolean) session.getAttribute("isLogin")) {
            session.setAttribute("isLogin", false);
            logger.info("User '" + request.getSession().getAttribute("username") + "' has been logout.");
            session.invalidate();
            try {
                response.sendRedirect("/admission/");
            } catch (IOException e) {
                logger.error("Failed to logout user.", e);
            }
        }
    }
}
