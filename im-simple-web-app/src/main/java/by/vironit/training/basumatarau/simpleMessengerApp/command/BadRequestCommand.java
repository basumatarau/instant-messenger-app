package by.vironit.training.basumatarau.simpleMessengerApp.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BadRequestCommand extends Command {
    @Override
    public Command process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String queryString = req.getQueryString();
        if(queryString == null){
            req.setAttribute(
                    "details" ,
                    req.getRequestURL()
                            .toString());

        }else{
            req.setAttribute(
                    "details" ,
                    req.getRequestURL()
                            .append('?')
                            .append(queryString)
                            .toString());

        }

        return null;
    }

    @Override
    public String getViewName() {
        return "bad-request";
    }
}
