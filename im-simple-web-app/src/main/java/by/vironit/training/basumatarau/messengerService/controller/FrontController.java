package by.vironit.training.basumatarau.messengerService.controller;

import by.vironit.training.basumatarau.messengerService.command.Command;
import by.vironit.training.basumatarau.messengerService.connection.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(FrontController.class);
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
            logger.info("controller exception: " + e.getLocalizedMessage());
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
