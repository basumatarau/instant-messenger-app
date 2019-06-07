package by.vironit.training.basumatarau.instantMessengerApp.command;

import by.vironit.training.basumatarau.instantMessengerApp.exception.ControllerException;
import by.vironit.training.basumatarau.instantMessengerApp.exception.DaoException;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

public abstract class Command {
    public abstract Command process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, DaoException, ParseException, ControllerException, ValidationException;

    public String getViewName() {
        final String className = getClass().getName();
        return className
                .substring(className.lastIndexOf(".") + 1)
                .replaceAll("Command$", "")
                .toLowerCase();
    }

    @Override
    public String toString() {
        return getClass().getName()
                .replaceFirst(".*command[.]","")
                .replaceAll("Command$", "");
    }
}
