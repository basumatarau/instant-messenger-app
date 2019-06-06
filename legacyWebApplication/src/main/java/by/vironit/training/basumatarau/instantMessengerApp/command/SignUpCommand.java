package by.vironit.training.basumatarau.instantMessengerApp.command;

import by.vironit.training.basumatarau.instantMessengerApp.controller.Action;
import by.vironit.training.basumatarau.instantMessengerApp.controller.RequestHandler;
import by.vironit.training.basumatarau.instantMessengerApp.controller.SessionHandler;
import by.vironit.training.basumatarau.instantMessengerApp.exception.*;
import by.vironit.training.basumatarau.instantMessengerApp.model.Role;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;
import by.vironit.training.basumatarau.instantMessengerApp.service.UserService;
import by.vironit.training.basumatarau.instantMessengerApp.service.impl.UserServiceImpl;
import by.vironit.training.basumatarau.instantMessengerApp.util.PasswordEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;

public class SignUpCommand extends Command {
    private UserService userService;
    private static final String EMAIL_REG_PATTERN = "([-_А-Яа-яЁё\\w\\d]{1,40})@([-_А-Яа-яЁё\\w\\d]{1,20}).([-_А-Яа-яЁё\\w\\d]{1,4})";
    private final static String LOGIN_REG_PATTERN = "(.{1,60})";
    private final static String FNAME_REG_PATTERN = "([-_А-Яа-яЁё\\w\\d]{1,150})";
    private final static String LNAME_REG_PATTERN = "([-_А-Яа-яЁё\\w\\d]{1,150})";
    private final static String NICK_REG_PATTERN = "([-_А-Яа-яЁё\\w\\d]{1,60})";

    public SignUpCommand(){
        init();
    }

    private void init(){
        this.userService = new UserServiceImpl();
    }

    @Override
    public Command process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, ValidationException, ControllerException {

        final User authorizedUser = SessionHandler.getAuthorizedUser(req);
        if(authorizedUser!=null){
            return Action.USERPROFILE.getCommand();
        }

        if(RequestHandler.isPost(req, resp)){
            final String email = RequestHandler.getString(req, "email", EMAIL_REG_PATTERN);
            final String login = RequestHandler.getString(req, "login", LOGIN_REG_PATTERN);
            final String firstName = RequestHandler.getString(req, "firstName", FNAME_REG_PATTERN);
            final String lastName = RequestHandler.getString(req, "lastName", LNAME_REG_PATTERN);
            final String nickName = RequestHandler.getString(req, "nickName", NICK_REG_PATTERN);


            try { userService.findUserByEmail(email)
                        .orElseThrow(() -> new UserCredentialsOccupied("email occupied"));
            } catch (ServiceException e) {
                //logger.log
                return Action.ERROR.getCommand();
            }

            byte[] salt = new byte[16];
            new SecureRandom().nextBytes(salt);
            final String pwdHash = PasswordEncoder.getPwdHash(login, salt);

            try {
                //todo fix the stub
                final Role stub = new Role.RoleBuilder().id(1).name("stub").build();

                final User newUser = new User.UserBuilder()
                        .enabled(true)
                        .email(email)
                        .firstName(firstName)
                        .lastName(lastName)
                        .nickName(nickName)
                        .role(stub)
                        .passwordHash(pwdHash)
                        .salt(salt)
                        .build();
                userService.registerNewUserAccount(newUser);
            } catch (ServiceException | InstantiationException e) {
                //logger.log
                return Action.ERROR.getCommand();
            }

            return Action.LOGINATION.getCommand();
        }

        return null;
    }
}
