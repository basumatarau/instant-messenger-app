package by.vironit.training.basumatarau.instantMessengerApp.command;

import by.vironit.training.basumatarau.instantMessengerApp.controller.Action;
import by.vironit.training.basumatarau.instantMessengerApp.controller.RequestHandler;
import by.vironit.training.basumatarau.instantMessengerApp.controller.SessionHandler;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ControllerException;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ServiceException;
import by.vironit.training.basumatarau.instantMessengerApp.exception.UserNotFound;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ValidationException;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;
import by.vironit.training.basumatarau.instantMessengerApp.service.UserService;
import by.vironit.training.basumatarau.instantMessengerApp.service.impl.UserServiceImpl;
import by.vironit.training.basumatarau.instantMessengerApp.util.PasswordEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginationCommand extends Command {
    private UserService userService;
    private static final String EMAIL_REG_PATTERN = "([-_А-Яа-яЁё\\w\\d]{1,40})@([-_А-Яа-яЁё\\w\\d]{1,20}).([-_А-Яа-яЁё\\w\\d]{1,4})";
    private final static String LOGIN_REG_PATTERN = "([-_А-Яа-яЁё\\w\\d]{1,60})";

    public LoginationCommand(){
        init();
    }

    private void init(){
        this.userService = new UserServiceImpl();
    }

    @Override
    public Command process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, ValidationException, ControllerException {

        final User authorizedUser = SessionHandler.getAuthorizedUser(req);

        if(RequestHandler.isPost(req, resp)){
            if(authorizedUser != null){
                return Action.USERPROFILE.getCommand();
            }else{
                final String email = RequestHandler.getString(req, "email", EMAIL_REG_PATTERN);
                final String login = RequestHandler.getString(req, "login", LOGIN_REG_PATTERN);

                final User retrievedUser;
                try {
                    retrievedUser = userService.findUserByEmail(email)
                            .orElseThrow(() -> new UserNotFound("wrong password or login"));
                } catch (ServiceException e) {
                    //logger.log
                    return Action.ERROR.getCommand();
                }
                //
                //PasswordEncoder.getPwdHash()

                SessionHandler.putAuthorizedUser(req, retrievedUser, false);
                return Action.USERPROFILE.getCommand();
            }
        }
        return null;
    }
}
