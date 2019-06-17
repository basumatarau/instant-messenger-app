package by.vironit.training.basumatarau.instantMessengerApp.command;

import by.vironit.training.basumatarau.instantMessengerApp.exception.ControllerException;
import by.vironit.training.basumatarau.instantMessengerApp.exception.DaoException;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

public class WsTest extends Command {
    @Override
    public Command process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, DaoException, ParseException, ControllerException, ValidationException {
        return null;
    }

    @Override
    public String getViewName() {
        return "wstest";
    }
}
