package by.vironit.training.basumatarau.messengerService.command;

import by.vironit.training.basumatarau.messengerService.controller.Action;
import by.vironit.training.basumatarau.messengerService.validator.RequestHandler;
import by.vironit.training.basumatarau.messengerService.controller.SessionHandler;
import by.vironit.training.basumatarau.messengerService.dao.ContactDao;
import by.vironit.training.basumatarau.messengerService.dao.DaoProvider;
import by.vironit.training.basumatarau.messengerService.exception.*;
import by.vironit.training.basumatarau.messengerService.model.User;
import by.vironit.training.basumatarau.messengerService.service.ContactService;
import by.vironit.training.basumatarau.messengerService.service.ServiceProvider;
import by.vironit.training.basumatarau.messengerService.service.UserService;
import by.vironit.training.basumatarau.messengerService.util.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginationCommand extends Command {

    private static final Logger logger = LoggerFactory.getLogger(LoginationCommand.class);

    private UserService userService;
    private ContactService contactService;
    private ContactDao contactDao;

    private static final String EMAIL_REG_PATTERN = "([-_А-Яа-яЁё\\w\\d]{1,40})@([-_А-Яа-яЁё\\w\\d]{1,20}).([-_А-Яа-яЁё\\w\\d]{1,4})";
    private final static String LOGIN_REG_PATTERN = "([-_А-Яа-яЁё\\w\\d]{1,60})";

    public LoginationCommand(){
        init();
    }

    private void init(){
        this.userService = ServiceProvider.SERV.userService;
        this.contactService = ServiceProvider.SERV.contactService;
        this.contactDao = DaoProvider.DAO.contactDao;
    }

    @Override
    public Command process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, ValidationException, ControllerException {

        final User authorizedUser = SessionHandler.getAuthorizedUser(req);

        if(RequestHandler.isPost(req, resp)){
            if(authorizedUser != null){
                return Action.USERPROFILE.getCommand();
            }else{
                final String email = RequestHandler.getString(req, "logininput", EMAIL_REG_PATTERN);
                final String password = RequestHandler.getString(req, "passwordinput", LOGIN_REG_PATTERN);

                final User retrievedUser;
                try {
                    retrievedUser = userService.findUserByEmail(email)
                            .orElseThrow(() -> new UserNotFound("wrong password or login"));
                } catch (ServiceException e) {
                    logger.warn("failed to fetch user with login: " + email);
                    return Action.ERROR.getCommand();
                }
                final String passwordHash = retrievedUser.getPasswordHash();
                final byte[] salt = retrievedUser.getSalt();
                if(!PasswordEncoder.getPwdHash(password,salt).equals(passwordHash)){
                    logger.warn("unsuccessful login attempt with login: " + email);
                    throw new UserNotFound("wrong password or login");
                }

                SessionHandler.putAuthorizedUser(req, retrievedUser, false);
                return Action.USERPROFILE.getCommand();
            }
        }else if(RequestHandler.isGet(req, resp)){
            SessionHandler.removeAuthorizedUser(req);
            req.getSession(false).invalidate();
            return Action.LOGINATION.getCommand();
        }
        return null;
    }

    @Override
    public String getViewName() {
        return "login-page";
    }
}
