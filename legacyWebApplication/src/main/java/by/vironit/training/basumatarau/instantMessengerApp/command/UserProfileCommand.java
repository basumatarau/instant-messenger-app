package by.vironit.training.basumatarau.instantMessengerApp.command;

import by.vironit.training.basumatarau.instantMessengerApp.controller.SessionHandler;
import by.vironit.training.basumatarau.instantMessengerApp.exception.*;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;
import by.vironit.training.basumatarau.instantMessengerApp.service.ServiceProvider;
import by.vironit.training.basumatarau.instantMessengerApp.service.UserService;
import by.vironit.training.basumatarau.instantMessengerApp.util.PasswordEncoder;
import by.vironit.training.basumatarau.instantMessengerApp.validator.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;

public class UserProfileCommand extends Command {

    private static final Logger logger = LoggerFactory.getLogger(SignUpCommand.class);

    private UserService userService;
    private static final String EMAIL_REG_PATTERN = "([-_А-Яа-яЁё\\w\\d]{3,40})@([-_А-Яа-яЁё\\w\\d]{1,20}).([-_А-Яа-яЁё\\w\\d]{1,4})";
    private final static String FNAME_REG_PATTERN = "([-_А-Яа-яЁё\\w\\d]{0,150})";
    private final static String LNAME_REG_PATTERN = "([-_А-Яа-яЁё\\w\\d]{0,150})";
    private final static String NICK_REG_PATTERN = "([-_А-Яа-яЁё\\w\\d]{1,60})";
    private final static String PASSWORD_REG_PATTERN = "[-_+\\d\\w]+";

    public UserProfileCommand() {
        init();
    }

    private void init() {
        this.userService = ServiceProvider.SERV.userService;
    }

    @Override
    public Command process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, ValidationException, ControllerException {

        final User authorizedUser = SessionHandler.getAuthorizedUser(req.getSession());

        if (RequestHandler.isPost(req, resp) && RequestHandler.getString(req, "updatePassword") == null) {
            final String email = RequestHandler.getString(req, "emailInput", EMAIL_REG_PATTERN);
            final String firstName = RequestHandler.getString(req, "firstNameInput", FNAME_REG_PATTERN);
            final String lastName = RequestHandler.getString(req, "lastNameInput", LNAME_REG_PATTERN);
            final String nickName = RequestHandler.getString(req, "nickNameInput", NICK_REG_PATTERN);

            final User toBeUpdated;
            try {
                toBeUpdated = new User.UserBuilder()
                        .id(authorizedUser.getId())
                        .role(authorizedUser.getRole())
                        .salt(authorizedUser.getSalt())
                        .passwordHash(authorizedUser.getPasswordHash())
                        .enabled(authorizedUser.getEnabled())
                        .email(email)
                        .nickName(nickName)
                        .lastName(lastName)
                        .firstName(firstName)
                        .build();

                userService.updateUser(toBeUpdated);
                SessionHandler.putAuthorizedUser(req, toBeUpdated, true);
                req.setAttribute("message", "user profile changed successfully");
            } catch (InstantiationException | ServiceException e) {
                logger.info("failed to update user: " + authorizedUser);
                req.setAttribute("error", e.getLocalizedMessage());
            }
        } else if (RequestHandler.isPost(req, resp) && RequestHandler.getString(req, "updatePassword") != null) {
            final String oldpassword = RequestHandler.getString(req, "oldpasswordInput", PASSWORD_REG_PATTERN);
            final String newpassword = RequestHandler.getString(req, "newpasswordInput", PASSWORD_REG_PATTERN);

            final byte[] salt = authorizedUser.getSalt();
            final String passwordHash = authorizedUser.getPasswordHash();

            final User toBeUpdated;
            if (!PasswordEncoder.getPwdHash(oldpassword, salt).equals(passwordHash)) {
                logger.info("unsuccessful password change for user: " + authorizedUser);
                throw new UserNotFound("wrong password");
            } else {
                final byte[] newSalt = new byte[16];
                new SecureRandom().nextBytes(salt);
                final String newPwdHash = PasswordEncoder.getPwdHash(newpassword, salt);

                try {
                    toBeUpdated = new User.UserBuilder()
                            .id(authorizedUser.getId())
                            .role(authorizedUser.getRole())
                            .salt(newSalt)
                            .passwordHash(newPwdHash)
                            .enabled(authorizedUser.getEnabled())
                            .email(authorizedUser.getEmail())
                            .nickName(authorizedUser.getNickName())
                            .lastName(authorizedUser.getLastName())
                            .firstName(authorizedUser.getFirstName())
                            .build();
                    userService.updateUser(toBeUpdated);
                    SessionHandler.putAuthorizedUser(req, toBeUpdated, true);
                    req.setAttribute("message", "password changed successfully");
                } catch (InstantiationException | ServiceException e) {
                    logger.info("failed to update user: " + authorizedUser);
                    req.setAttribute("error", e.getLocalizedMessage());
                }
            }
        }
        req.setAttribute("user", SessionHandler.getAuthorizedUser(req.getSession()));
        return null;
    }

    @Override
    public String getViewName() {
        return "user-profile-page";
    }
}
