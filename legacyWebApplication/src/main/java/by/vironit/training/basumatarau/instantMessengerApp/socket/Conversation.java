package by.vironit.training.basumatarau.instantMessengerApp.socket;

import by.vironit.training.basumatarau.instantMessengerApp.controller.FrontController;
import by.vironit.training.basumatarau.instantMessengerApp.dto.IncomingMessageDto;
import by.vironit.training.basumatarau.instantMessengerApp.dto.MessageDto;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ControllerException;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ServiceException;
import by.vironit.training.basumatarau.instantMessengerApp.model.Contact;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;
import by.vironit.training.basumatarau.instantMessengerApp.service.ContactService;
import by.vironit.training.basumatarau.instantMessengerApp.service.MessageService;
import by.vironit.training.basumatarau.instantMessengerApp.service.ServiceProvider;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;

@ServerEndpoint(value = "/messaging/{userId}",
        configurator = GetHttpSessionConfigurator.class,
        decoders = MessageDecoder.class,
        encoders = MessageEncoder.class )
public class Conversation {

    private HttpSession httpSession;
    private User authorizedUser;
    private Map<User, Session> activeSessions;

    private ContactService contactService = ServiceProvider.SERV.contactService;
    private MessageService messageService = ServiceProvider.SERV.messageService;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws IOException {
        this.httpSession = (HttpSession) config.getUserProperties()
                .get(HttpSession.class.getName());

        final Object authUser = config.getUserProperties()
                .get(GetHttpSessionConfigurator.AUTHORIZED_USER_ATTR_NAME);
        if (authUser == null) {
            onClose(session);
        }else{
            this.authorizedUser = ((User) authUser);
        }

        this.activeSessions
                = (Map<User, Session>) httpSession.getServletContext()
                .getAttribute(FrontController.ACTIVE_WSSESSIONS_ATTR_NAME);

        activeSessions.put(authorizedUser, session);
    }

    @OnMessage
    public void onMessage(Session session, IncomingMessageDto message) throws IOException {
        try {
            final Contact contact = contactService.findContactById(message.getContactId())
                    .orElseThrow(() -> new ControllerException("failed to retrieve contact"));

            final MessageDto messageDto = messageService.persistMessage(message, contact);

            final Session ownerSession = activeSessions.get(contact.getOwner());
            if(ownerSession!=null){
                ownerSession.getBasicRemote().sendObject(messageDto);
            }

            final Session personSession = activeSessions.get(contact.getPerson());
            if(personSession!=null){
                personSession.getBasicRemote().sendObject(messageDto);
            }

        } catch (EncodeException | ServiceException | ControllerException e) {
            //todo something about it...
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        activeSessions.remove(authorizedUser);
        // WebSocket connection closes
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }
}
