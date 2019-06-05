package by.vironit.training.basumatarau.instantMessengerApp.command;

import by.vironit.training.basumatarau.instantMessengerApp.exception.DaoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {
    Command process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, DaoException;

    default String getViewName(){
        //todo fix view resolution
        return "/WEB-INF/" + this.getClass()
                .getName()
                .substring(getClass().getName().lastIndexOf(".")+1)
                .replaceAll("Command$", "")
                .toLowerCase()+".jsp";
    }
}
