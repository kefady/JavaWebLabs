package com.universityadmissions.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/controller")
public class FrontController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        execute(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        execute(request, response);
    }

    private void execute(HttpServletRequest req, HttpServletResponse resp) {
        Command command = CommandFactory.getCommand(req);
        command.execute(req, resp);
    }
}
