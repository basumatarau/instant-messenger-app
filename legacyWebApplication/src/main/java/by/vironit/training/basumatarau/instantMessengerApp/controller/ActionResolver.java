package by.vironit.training.basumatarau.instantMessengerApp.controller;

import javax.servlet.http.HttpServletRequest;

public class ActionResolver {
    Action resolve(HttpServletRequest request) {
        Action action = Action.ERROR;
        String command = request.getParameter("command");

        if (command == null) {
            request.setAttribute("printStackTrace", "illegal argument error");
            return action;
        }
        try {
            action = Action.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            request.setAttribute("printStackTrace", e.toString());
        }
        return action;
    }
}
