package com.universityadmissions.controller;

import com.universityadmissions.dao.mysql.MysqlDaoFactory;
import jakarta.servlet.http.HttpServletRequest;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandFactory {
    private CommandFactory() {

    }

    public static Command getCommand(HttpServletRequest request) {
        String action = request.getParameter("action");
        Command command = null;
        if (action != null) {
            try {
                command = CommandEnum.valueOf(action.toUpperCase()).getCommand();
            } catch (IllegalArgumentException e) {
                Logger.getLogger(CommandFactory.class.getName()).log(Level.WARNING, "Command no found.", e);
            }
        }
        return command;
    }
}
