package by.vironit.training.basumatarau.instantMessengerApp.command;

import by.vironit.training.basumatarau.instantMessengerApp.controller.Action;
import by.vironit.training.basumatarau.instantMessengerApp.validator.RequestHandler;
import by.vironit.training.basumatarau.instantMessengerApp.controller.SessionHandler;
import by.vironit.training.basumatarau.instantMessengerApp.dao.DaoProvider;
import by.vironit.training.basumatarau.instantMessengerApp.exception.*;
import by.vironit.training.basumatarau.instantMessengerApp.model.Role;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;
import by.vironit.training.basumatarau.instantMessengerApp.service.ServiceProvider;
import by.vironit.training.basumatarau.instantMessengerApp.service.UserService;
import by.vironit.training.basumatarau.instantMessengerApp.util.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Optional;

public class SignUpCommand extends Command {

    private static final Logger logger = LoggerFactory.getLogger(SignUpCommand.class);

    private UserService userService;
    private static final String EMAIL_REG_PATTERN = "([-_А-Яа-яЁё\\w\\d]{3,40})@([-_А-Яа-яЁё\\w\\d]{1,20}).([-_А-Яа-яЁё\\w\\d]{1,4})";
    private final static String FNAME_REG_PATTERN = "([-_А-Яа-яЁё\\w\\d]{0,150})";
    private final static String LNAME_REG_PATTERN = "([-_А-Яа-яЁё\\w\\d]{0,150})";
    private final static String NICK_REG_PATTERN = "([-_А-Яа-яЁё\\w\\d]{1,60})";
    private final static String PASSWORD_REG_PATTERN = "[-_+\\d\\w]+";

    public SignUpCommand(){
        init();
    }

    private void init(){
        this.userService = ServiceProvider.SERV.userService;
    }

    @Override
    public Command process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, ValidationException, ControllerException {

        final User authorizedUser = SessionHandler.getAuthorizedUser(req);
        if(authorizedUser!=null){
            return Action.USERPROFILE.getCommand();
        }

        if(RequestHandler.isPost(req, resp)){
            final String email = RequestHandler.getString(req, "emailInput", EMAIL_REG_PATTERN);
            final String password = RequestHandler.getString(req, "passwordInput", PASSWORD_REG_PATTERN );
            final String firstName = RequestHandler.getString(req, "firstNameInput", FNAME_REG_PATTERN);
            final String lastName = RequestHandler.getString(req, "lastNameInput", LNAME_REG_PATTERN);
            final String nickName = RequestHandler.getString(req, "nickNameInput", NICK_REG_PATTERN);

            try {
                if(userService
                        .findUserByEmail(email)
                        .isPresent()) {
                    throw new UserCredentialsOccupied("user credentials occupied for email: " + email);
                }
            } catch (ServiceException e) {
                logger.warn("email look up failure during sign-up for email: " + email);
                return Action.ERROR.getCommand();
            }

            byte[] salt = new byte[16];
            new SecureRandom().nextBytes(salt);
            final String pwdHash = PasswordEncoder.getPwdHash(password, salt);

            try {
                final Role freshUserRole;
                try {
                    final Optional<Role> optRole = DaoProvider.DAO.roleDao.findByName("USER");
                    freshUserRole = optRole.orElseThrow(() -> new DaoException("no USER role present in db"));
                } catch (DaoException e) {
                    logger.error("sign-up failure for: " + email);
                    throw new ServiceException("failed to fetch roles", e);
                }

                final User newUser = new User.UserBuilder()
                        .enabled(true)
                        .email(email)
                        .firstName(firstName)
                        .lastName(lastName)
                        .nickName(nickName)
                        .role(freshUserRole)
                        .passwordHash(pwdHash)
                        .salt(salt)
                        .build();
                userService.registerNewUserAccount(newUser);
            } catch (ServiceException | InstantiationException e) {
                logger.error("persistence failure at sign-up for user with email: " + email);
                return Action.ERROR.getCommand();
            }
            return Action.LOGINATION.getCommand();
        }

        return null;
    }

    @Override
    public String getViewName() {
        return "sign-up";
    }
}
