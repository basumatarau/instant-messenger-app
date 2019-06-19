package by.vironit.training.basumatarau.instantMessengerApp.command;

import by.vironit.training.basumatarau.instantMessengerApp.controller.Action;
import by.vironit.training.basumatarau.instantMessengerApp.controller.RequestHandler;
import by.vironit.training.basumatarau.instantMessengerApp.controller.SessionHandler;
import by.vironit.training.basumatarau.instantMessengerApp.dto.ContactVo;
import by.vironit.training.basumatarau.instantMessengerApp.dto.UserDto;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ControllerException;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ServiceException;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ValidationException;
import by.vironit.training.basumatarau.instantMessengerApp.model.Contact;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;
import by.vironit.training.basumatarau.instantMessengerApp.service.ContactService;
import by.vironit.training.basumatarau.instantMessengerApp.service.ServiceProvider;
import by.vironit.training.basumatarau.instantMessengerApp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ContactListCommand extends Command {

    private static final Logger logger = LoggerFactory.getLogger(ContactListCommand.class);
    //request attributes
    private final static String LIKE_PARAM = "like";
    private final static String SEND_REQUEST = "sendRequest";
    private final static String DELETE_CONTACT = "deleteContact";
    private final static String CONFIRM_REQUEST = "confirmRequest";
    private final static String UNFRIEND_REQUEST = "unfriend";
    private final static String DECLINE_REQUEST = "declineRequest";
    private final static String CONTACT = "contactId";

    private final ContactService contactService;
    private final UserService userService;

    public ContactListCommand() {
        this.contactService = ServiceProvider.SERV.contactService;
        this.userService = ServiceProvider.SERV.userService;
    }

    @Override
    public Command process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, ValidationException, ControllerException {
        final User authorizedUser = SessionHandler.getAuthorizedUser(req);
        List<ContactVo> contactsForUser;
        List<UserDto> users;

        final String likeParam = req.getParameter(LIKE_PARAM) != null ?
                RequestHandler.getString(req, LIKE_PARAM) : null;
        final Long sendRequestToUserWithId = req.getParameter(SEND_REQUEST) != null ?
                RequestHandler.getLong(req, SEND_REQUEST) : null;
        final Long deleteUserContactWithId = req.getParameter(DELETE_CONTACT) != null ?
                RequestHandler.getLong(req, DELETE_CONTACT) : null;
        final Long contactId = req.getParameter(CONTACT) != null ?
                RequestHandler.getLong(req, CONTACT) : null;
        final Long unfriednUserId = req.getParameter(UNFRIEND_REQUEST) != null ?
                RequestHandler.getLong(req, UNFRIEND_REQUEST) : null;

        if (likeParam != null) {
            if (searchUsers(req, authorizedUser, likeParam)) {
                return Action.ERROR.getCommand();
            }
        } else if (sendRequestToUserWithId != null) {

            try {
                User owner = userService.findUserById(sendRequestToUserWithId)
                        .orElseThrow(() -> new ControllerException("user not found"));
                final boolean present = contactService.getContactByOwnerAndUser(owner, authorizedUser)
                        .isPresent();
                if (present) {
                    req.setAttribute("message", "contact is already pending");
                }
                contactService.sendContactRequestToUser(owner, authorizedUser);
            } catch (ServiceException e) {
                logger.info("failed to send request to send friend request to user with id: "
                        + sendRequestToUserWithId);
                return Action.ERROR.getCommand();
            }

            if (getAndRenderUserContacts(req, authorizedUser)) {
                return Action.ERROR.getCommand();
            }
        } else if (deleteUserContactWithId != null) {
            if (deleteContactWithUserId(authorizedUser, deleteUserContactWithId)) {
                return Action.ERROR.getCommand();
            }
            if (getAndRenderUserContacts(req, authorizedUser)) {
                return Action.ERROR.getCommand();
            }
        } else if (req.getParameter(CONFIRM_REQUEST) != null) {
            if (confirmFriendRequest(authorizedUser, contactId)) {
                return Action.ERROR.getCommand();
            }
            if (getAndRenderUserContacts(req, authorizedUser)) {
                return Action.ERROR.getCommand();
            }
        } else if (unfriednUserId != null) {
            if (unfriendUser(authorizedUser, unfriednUserId)) {
                return Action.ERROR.getCommand();
            }
            if (getAndRenderUserContacts(req, authorizedUser)) {
                return Action.ERROR.getCommand();
            }
        } else if (req.getParameter(DECLINE_REQUEST) != null) {
            if (declineFriendRequestWithId(contactId)) {
                return Action.ERROR.getCommand();
            }
            if (getAndRenderUserContacts(req, authorizedUser)) {
                return Action.ERROR.getCommand();
            }
        } else {
            if (getAndRenderUserContacts(req, authorizedUser)) {
                return Action.ERROR.getCommand();
            }
        }
        return null;
    }

    private boolean declineFriendRequestWithId(Long contactId) throws ControllerException {
        try {
            final Contact contact = contactService.findContactById(contactId)
                    .orElseThrow(() -> new ControllerException("no contact found"));
            contactService.declineContactRequest(contact);
        } catch (ServiceException e) {
            logger.info("failed to decline friend request with id: "
                    + contactId);
            return true;
        }
        return false;
    }

    private boolean searchUsers(HttpServletRequest req, User authorizedUser, String likeParam) {
        List<UserDto> users;
        try {
            users = userService.searchUsersWithPattern(authorizedUser, likeParam);
            req.setAttribute("userSearchResults", users);
        } catch (ServiceException e) {
            logger.info("failed to fetch users with look up pattern (for authorized user: " +
                    authorizedUser + "): "
                    + likeParam);
            return true;
        }
        return false;
    }

    private boolean sendFriendRequestToUserWithId(User authorizedUser, Long sendRequestToUserWithId) throws ControllerException {

        return false;
    }

    private boolean deleteContactWithUserId(User authorizedUser, Long deleteUserContactWithId) throws ControllerException {
        try {
            final Contact contact
                    = contactService.findContactById(deleteUserContactWithId)
                    .orElseThrow(() -> new ControllerException("contact not found"));
            if (!contact.getOwner().equals(authorizedUser)) {
                logger.warn("failed to fetch contact by id: " + deleteUserContactWithId +
                        "for user: " + authorizedUser);
                throw new ControllerException("contact not found");
            }

            contactService.removeContact(contact);
            if (contact.getIsConfirmed()) {
                final Optional<Contact> cont
                        = contactService.getContactByOwnerAndUser(contact.getPerson(), contact.getOwner());
                if (cont.isPresent()) {
                    contactService.removeContact(cont.get());
                }
            }
        } catch (ServiceException e) {
            logger.warn("failed to delete user with id: " + deleteUserContactWithId);
            return true;
        }
        return false;
    }

    private boolean confirmFriendRequest(User authorizedUser, Long contactId) throws ControllerException {
        try {
            final Contact contact
                    = contactService.findContactById(contactId)
                    .orElseThrow(() -> new ControllerException("no contact found"));
            if (!contact.getOwner().equals(authorizedUser)) {
                logger.warn("failed to fetch contact by id: " + contactId);
                throw new ControllerException("no contact found");
            }
            contactService.confirmContactRequest(contact);
        } catch (ServiceException e) {
            logger.warn("failed to confirm contact with id: " + contactId +
                    "for user: " + authorizedUser);
            return true;
        }
        return false;
    }

    private boolean getAndRenderUserContacts(HttpServletRequest req, User authorizedUser) {
        List<ContactVo> contactsForUser;
        try {
            contactsForUser = contactService.findAllContactsForUser(authorizedUser);
            req.setAttribute("userContacts", contactsForUser);
        } catch (ServiceException e) {
            logger.warn("failed to fetch all contacts for user: " + authorizedUser);
            return true;
        }
        req.setAttribute("userContacts", contactsForUser);
        return false;
    }

    private boolean unfriendUser(User authorizedUser, Long unfriednUserId) throws ControllerException {
        try {
            final User unfriendedUser = userService.findUserById(unfriednUserId)
                    .orElseThrow(() -> new ControllerException("no user found"));

            contactService.removeContactForOwnerAndUser(unfriendedUser, authorizedUser);
        } catch (ServiceException e) {
            logger.warn("failed to unfriend user with id: " + unfriednUserId +
                    " by authorized user: " + authorizedUser);
            return true;
        }
        return false;
    }

    @Override
    public String getViewName() {
        return "contact-list";
    }
}
