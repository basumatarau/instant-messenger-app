package by.vironit.training.basumatarau.instantMessengerApp.controller;

import by.vironit.training.basumatarau.instantMessengerApp.model.User;
import javax.servlet.http.HttpServletRequest;

public class SessionHandler {
    private static final String AUTHORIZED_USER = "user";
    private static final String AUTHORIZED_USER_CONTACTS = "userContacts";

    private SessionHandler() {
    }

    public static User getAuthorizedUser(HttpServletRequest req) {
        return (User) req.getSession().getAttribute(AUTHORIZED_USER);
    }

    public static void putAuthorizedUser(HttpServletRequest req, User authUser, boolean override) {
        if (getAuthorizedUser(req) == null || override) {
            synchronized (req.getSession().getId()) {
                if (getAuthorizedUser(req) == null || override) {
                    req.getSession().setAttribute(AUTHORIZED_USER, authUser);
                }
            }
        }
    }

    public static void removeAuthorizedUser(HttpServletRequest req) {
        if (getAuthorizedUser(req) != null) {
            synchronized (req.getSession().getId()) {
                if (getAuthorizedUser(req) == null) {
                    req.getSession().removeAttribute(AUTHORIZED_USER);
                }
            }
        }
    }
}
