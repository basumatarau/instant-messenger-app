package by.vironit.training.basumatarau.simpleMessengerApp.command;

import by.vironit.training.basumatarau.simpleMessengerApp.controller.Action;
import by.vironit.training.basumatarau.simpleMessengerApp.validator.RequestHandler;
import by.vironit.training.basumatarau.simpleMessengerApp.controller.SessionHandler;
import by.vironit.training.basumatarau.simpleMessengerApp.dto.ContactVo;
import by.vironit.training.basumatarau.simpleMessengerApp.dto.MessageDto;
import by.vironit.training.basumatarau.simpleMessengerApp.exception.ServiceException;
import by.vironit.training.basumatarau.simpleMessengerApp.exception.ValidationException;
import by.vironit.training.basumatarau.simpleMessengerApp.model.Contact;
import by.vironit.training.basumatarau.simpleMessengerApp.service.ContactService;
import by.vironit.training.basumatarau.simpleMessengerApp.service.MessageService;
import by.vironit.training.basumatarau.simpleMessengerApp.service.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ChatCommand extends Command {

    private static final Logger logger = LoggerFactory.getLogger(ChatCommand.class);

    private final ContactService contactService = ServiceProvider.SERV.contactService;
    private final MessageService messageService = ServiceProvider.SERV.messageService;
    private final static String MESSAGE_TO_CONTACT_WITH_ID = "messageToContactId";

    @Override
    public Command process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, ValidationException {
        final Long messageParamId = req.getParameter(MESSAGE_TO_CONTACT_WITH_ID) != null ?
                RequestHandler.getLong(req, MESSAGE_TO_CONTACT_WITH_ID) : null;

        Map<ContactVo, MessageDto> contactsForUser;
        try {
            contactsForUser
                    = contactService.getContactsAndLastMessages(SessionHandler.getAuthorizedUser(req));
        } catch (ServiceException e) {
            logger.warn("failed to get contacts for authorized user");
            return Action.ERROR.getCommand();
        }
        req.setAttribute("contactsForUser", contactsForUser);

        if (messageParamId != null) {
            final Optional<Contact> contactById;
            try {
                contactById = contactService.findContactById(messageParamId);
            } catch (ServiceException e) {
                logger.warn("failed to find contact by id: " + messageParamId);
                return Action.ERROR.getCommand();
            }
            try {
                if (contactById.isPresent()) {
                    final ContactVo contact = contactById.map(ContactVo::getDto).get();
                    final List<MessageDto> messagesForContact
                            = messageService
                            .getMessagesForContact(contact);

                    if(!contactsForUser.containsKey(contact)) {
                        contactsForUser.put(contact, null);
                    }
                    req.setAttribute("currentContact", contactById.get());
                    req.setAttribute("conversation", messagesForContact);
                    //
                } else {
                    req.setAttribute("message", "contact not found");
                }
            } catch (ServiceException e) {
                logger.warn("failed to fetch contacts for authorized user");
                return Action.ERROR.getCommand();
            }
        }

        final String hostAddress;
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            hostAddress = socket.getLocalAddress().getHostAddress();
        }
        req.setAttribute("hostIpAddress", hostAddress);
        return null;
    }

    @Override
    public String getViewName() {
        return "chat-page";
    }
}
