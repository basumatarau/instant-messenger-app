package by.vironit.training.basumatarau.instantMessengerApp.controller;

import by.vironit.training.basumatarau.instantMessengerApp.command.Command;
import by.vironit.training.basumatarau.instantMessengerApp.connection.ConnectionPool;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public class FrontController extends HttpServlet {
    public static final String ACTIVE_WSSESSIONS_ATTR_NAME = "wssessions";
    private ActionResolver actionResolver;
    private ViewResolver viewResolver;

    @Override
    public void init() throws ServletException {
        super.init();
        ConnectionPool.getInstance();
        actionResolver = ActionResolver.getInstance();
        viewResolver = ViewResolver.getInstance();

        Map<Long, Session> sessions = Collections.synchronizedMap(new WeakHashMap<>());
        getServletContext().setAttribute(ACTIVE_WSSESSIONS_ATTR_NAME, sessions);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Action action = actionResolver.resolve(req);
        Command command = action.getCommand();

        Command nextCommand;
        try {
            nextCommand = command.process(req, resp);
        } catch (Exception e) {
            nextCommand = null;
            req.setAttribute("error", getInitCauseLocalizedMessage(e));
        }

        if (nextCommand == null || nextCommand == command) {
            final String viewPath
                    = viewResolver.resolve(command.getViewName());

            getServletContext()
                    .getRequestDispatcher(viewPath)
                    .forward(req, resp);
        } else {
            resp.sendRedirect("q?command=" + nextCommand);
        }

        resp.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
    }

    private Object getInitCauseLocalizedMessage(Throwable e) {
        while(e.getCause()!=null){
            e = e.getCause();
        }
        return e.getLocalizedMessage();
    }

    @Override
    public void destroy() {
        super.destroy();
        ConnectionPool.getInstance().dispose();
    }
}
