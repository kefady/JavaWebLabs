package com.universityadmissions.controller.command.admin;

import com.universityadmissions.controller.Command;
import com.universityadmissions.util.Election;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class StartElectionCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Election.start();
        request.getServletContext().setAttribute("isApplyEnd", true);
        ConsoleCommand consoleCommand = new ConsoleCommand();
        consoleCommand.execute(request, response);
    }
}
