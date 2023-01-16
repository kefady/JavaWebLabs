package com.universityadmissions.controller.command.admin;

import com.universityadmissions.controller.Command;
import com.universityadmissions.util.Election;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class StartElectionCommand implements Command {
    private static final Logger logger = Logger.getLogger(StartElectionCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Election.start();
        request.getServletContext().setAttribute("isApplyEnd", true);
        ConsoleCommand consoleCommand = new ConsoleCommand();
        consoleCommand.execute(request, response);
        logger.info("Election is started.");
    }
}
