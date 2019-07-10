package by.vironit.training.basumatarau.messengerService.command;

import by.vironit.training.basumatarau.messengerService.exception.DaoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorCommand extends Command {

    @Override
    public Command process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, DaoException {
        return null;
    }

    @Override
    public String getViewName() {
        return "bad-request";
    }
}
