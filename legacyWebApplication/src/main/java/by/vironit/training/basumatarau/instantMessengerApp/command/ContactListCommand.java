package by.vironit.training.basumatarau.instantMessengerApp.command;

import by.vironit.training.basumatarau.instantMessengerApp.controller.Action;
import by.vironit.training.basumatarau.instantMessengerApp.controller.RequestHandler;
import by.vironit.training.basumatarau.instantMessengerApp.controller.SessionHandler;
import by.vironit.training.basumatarau.instantMessengerApp.dto.ContactDto;
import by.vironit.training.basumatarau.instantMessengerApp.dto.UserDto;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ControllerException;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ServiceException;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ValidationException;
import by.vironit.training.basumatarau.instantMessengerApp.model.Contact;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;
import by.vironit.training.basumatarau.instantMessengerApp.service.ContactService;
import by.vironit.training.basumatarau.instantMessengerApp.service.ServiceProvider;
import by.vironit.training.basumatarau.instantMessengerApp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ContactListCommand extends Command {
    //request attributes
    private final static String LIKE_PARAM = "like";
    private final static String SEND_REQUEST = "sendRequest";
    private final static String DELETE_CONTACT = "deleteContact";
    private final static String CONFIRM_REQUEST = "confirmRequest";

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
        List<ContactDto> contactsForUser;
        List<UserDto> users;

        final String likeParam = req.getParameter(LIKE_PARAM) != null ?
                RequestHandler.getString(req, LIKE_PARAM) : null;
        final Long sendRequestToUserWithId = req.getParameter(SEND_REQUEST) != null ?
                RequestHandler.getLong(req, SEND_REQUEST) : null;
        final Long deleteUserContactWithId = req.getParameter(DELETE_CONTACT) != null ?
                RequestHandler.getLong(req, DELETE_CONTACT) : null;
        final Long confirmRequestWithId = req.getParameter(CONFIRM_REQUEST) != null ?
                RequestHandler.getLong(req, CONFIRM_REQUEST) : null;

        if (likeParam != null) {
            try {

                users = userService.searchUsersWithPattern(authorizedUser, likeParam);
                req.setAttribute("userSearchResults", users);
            } catch (ServiceException e) {
                //logger.log
                return Action.ERROR.getCommand();
            }
        }else if(sendRequestToUserWithId != null){
            try {

                User owner = userService.findUserById(sendRequestToUserWithId)
                        .orElseThrow(() -> new ControllerException("user not found"));

                final boolean present = contactService.getContactByOwnerAndUser(owner, authorizedUser)
                        .isPresent();
                if(present){
                    throw new ControllerException("contact is already pending");
                }

                contactService.sendContactRequestToUser(owner, authorizedUser);
            } catch (ServiceException e) {
                //logger.log
                return Action.ERROR.getCommand();
            }
        }else if(deleteUserContactWithId != null){
            try {
                final Contact contact
                        = contactService.findContactById(deleteUserContactWithId)
                        .orElseThrow(() -> new ControllerException("contact not found"));
                if(!contact.getOwner().equals(authorizedUser)){
                    throw new ControllerException("contact not found");
                    //logger.log + user ban if persisted ?
                }
                contactService.removeContact(contact);

                if(contact.getIsConfirmed()) {
                    final Optional<Contact> cont
                            = contactService.getContactByOwnerAndUser(authorizedUser, contact.getOwner());
                    if(cont.isPresent()){
                        contactService.removeContact(cont.get());
                    }
                }
            } catch (ServiceException e) {
                //logger.log
                return Action.ERROR.getCommand();
            }
        }else if(confirmRequestWithId != null){
            try {
                final Contact contact
                        = contactService.findContactById(confirmRequestWithId)
                        .orElseThrow(() -> new ControllerException("no contact found"));
                if(!contact.getOwner().equals(authorizedUser)){
                    throw new ControllerException("no contact found");
                    //logger.log + user ban if persisted ?
                }
                contactService.confirmContactRequest(contact);
            } catch (ServiceException e) {
                //logger.log
                return Action.ERROR.getCommand();
            }

            try {
                contactsForUser = contactService.findAllContactsForUser(authorizedUser);
                req.setAttribute("userContacts", contactsForUser);
            } catch (ServiceException e) {
                //logger.log
                return Action.ERROR.getCommand();
            }
            req.setAttribute("userContacts", contactsForUser);

        } else {
            try {
                contactsForUser = contactService.findAllContactsForUser(authorizedUser);
                req.setAttribute("userContacts", contactsForUser);
            } catch (ServiceException e) {
                //logger.log
                return Action.ERROR.getCommand();
            }
            req.setAttribute("userContacts", contactsForUser);
        }
        return null;
    }

    @Override
    public String getViewName() {
        return "contact-list";
    }
}
