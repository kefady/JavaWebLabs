package com.universityadmissions.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;


public class CommandFactory {
    private static final Logger logger = Logger.getLogger(CommandFactory.class);

    private CommandFactory() {

    }

    public static Command getCommand(HttpServletRequest request) {
        String action = request.getParameter("action");
        Command command = null;
        if (action != null) {
            try {
                command = CommandEnum.valueOf(action.toUpperCase()).getCommand();
            } catch (IllegalArgumentException e) {
                logger.error("Command '" + action + "' not found.", e);
            }
        }
        return command;
    }
}
