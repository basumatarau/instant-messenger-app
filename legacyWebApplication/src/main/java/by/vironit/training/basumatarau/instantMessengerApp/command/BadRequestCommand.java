package by.vironit.training.basumatarau.instantMessengerApp.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BadRequestCommand extends Command {
    @Override
    public Command process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return null;
    }

    @Override
    public String getViewName() {
        return "bad-request";
    }
}
