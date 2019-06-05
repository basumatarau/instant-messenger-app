package by.vironit.training.basumatarau.instantMessengerApp.controller;

import by.vironit.training.basumatarau.instantMessengerApp.command.Command;
import by.vironit.training.basumatarau.instantMessengerApp.connection.ConnectionPool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {

    private ActionResolver resolver;

    @Override
    public void init() throws ServletException {
        super.init();
        ConnectionPool.getInstance();
        resolver = new ActionResolver();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Action action = resolver.resolve(req);
        Command command = action.getCommand();

        Command nextCommand = null;
        try {
            nextCommand = command.process(req, resp);
        } catch (Exception e) {
            //todo set error attribute
        }

        if (nextCommand == null || nextCommand == command) {
            //todo fix view-model binding

            getServletContext()
                    .getRequestDispatcher(command.getViewName())
                    .forward(req, resp);
        } else {
            resp.sendRedirect("q?command=" + nextCommand);
        }

        //resp.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
        //resp.setHeader("Pragma", "no-cache");

    }

    @Override
    public void destroy() {
        super.destroy();
        ConnectionPool.getInstance().dispose();
    }
}
