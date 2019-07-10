package by.vironit.training.basumatarau.instantMessengerApp.controller;

import javax.servlet.http.HttpServletRequest;

public class ActionResolver {
    private static ActionResolver instance;

    private ActionResolver() {
    }

    static ActionResolver getInstance() {
        if (instance == null) {
            synchronized (ActionResolver.class) {
                if (instance == null) {
                    instance = new ActionResolver();
                }
            }
        }
        return instance;
    }

    Action resolve(HttpServletRequest request) {
        Action action = Action.ERROR;
        String command = request.getParameter("command");

        if (command == null || request.getAttribute("badRequest") != null) {
            return Action.BADREQUEST;
        }
        try {
            action = Action.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            request.setAttribute("printStackTrace", e.toString());
        }
        return action;
    }
}
