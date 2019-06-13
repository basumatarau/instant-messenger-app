package by.vironit.training.basumatarau.instantMessengerApp.command;

import by.vironit.training.basumatarau.instantMessengerApp.controller.Action;
import by.vironit.training.basumatarau.instantMessengerApp.controller.RequestHandler;
import by.vironit.training.basumatarau.instantMessengerApp.controller.SessionHandler;
import by.vironit.training.basumatarau.instantMessengerApp.dto.ContactDto;
import by.vironit.training.basumatarau.instantMessengerApp.dto.MessageDto;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ServiceException;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ValidationException;
import by.vironit.training.basumatarau.instantMessengerApp.model.Contact;
import by.vironit.training.basumatarau.instantMessengerApp.service.ContactService;
import by.vironit.training.basumatarau.instantMessengerApp.service.MessageService;
import by.vironit.training.basumatarau.instantMessengerApp.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ChatCommand extends Command {

    private final ContactService contactService = ServiceProvider.SERV.contactService;
    private final MessageService messageService = ServiceProvider.SERV.messageService;
    private final static String MESSAGE_TO_CONTACT_WITH_ID = "messageToContactId";

    @Override
    public Command process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, ValidationException {
        final Long messageParamId = req.getParameter(MESSAGE_TO_CONTACT_WITH_ID) != null ?
                RequestHandler.getLong(req, MESSAGE_TO_CONTACT_WITH_ID) : null;

        List<ContactDto> contactsForUser;
        try {
            contactsForUser =
                    contactService.findAllContactsForUser(SessionHandler.getAuthorizedUser(req));
        } catch (ServiceException e) {
            //logger.log
            return Action.ERROR.getCommand();
        }
        req.setAttribute("contactsForUser", contactsForUser);

        if (messageParamId != null) {
            final Optional<Contact> contactById;
            try {
                contactById = contactService.findContactById(messageParamId);
            } catch (ServiceException e) {
                //logger.log
                return Action.ERROR.getCommand();
            }
            try {
                if (contactById.isPresent()) {
                    final ContactDto contact = contactById.map(ContactDto::getDto).get();
                    final List<MessageDto> messagesForContact
                            = messageService
                            .getMessagesForContact(contact);

                    if(!contactsForUser.contains(contact)) {
                        contactsForUser.add(0, contact);
                    }else{
                        contactsForUser.remove(contact);
                        contactsForUser.add(0, contact);
                    }
                    req.setAttribute("conversation", messagesForContact);
                } else {
                    req.setAttribute("message", "contact not found");
                }
            } catch (ServiceException e) {
                //logger.log
                return Action.ERROR.getCommand();
            }
        }
        return null;
    }

    @Override
    public String getViewName() {
        return "chat-page";
    }
}
