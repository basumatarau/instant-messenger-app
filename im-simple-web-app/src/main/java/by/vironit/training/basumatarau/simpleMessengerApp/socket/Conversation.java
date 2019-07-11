package by.vironit.training.basumatarau.simpleMessengerApp.socket;

import by.vironit.training.basumatarau.simpleMessengerApp.controller.FrontController;
import by.vironit.training.basumatarau.simpleMessengerApp.dto.IncomingMessageDto;
import by.vironit.training.basumatarau.simpleMessengerApp.dto.MessageDto;
import by.vironit.training.basumatarau.simpleMessengerApp.dto.WsErrorDto;
import by.vironit.training.basumatarau.simpleMessengerApp.exception.ControllerException;
import by.vironit.training.basumatarau.simpleMessengerApp.exception.ServiceException;
import by.vironit.training.basumatarau.simpleMessengerApp.exception.ValidationException;
import by.vironit.training.basumatarau.simpleMessengerApp.model.Contact;
import by.vironit.training.basumatarau.simpleMessengerApp.model.User;
import by.vironit.training.basumatarau.simpleMessengerApp.service.ContactService;
import by.vironit.training.basumatarau.simpleMessengerApp.service.MessageService;
import by.vironit.training.basumatarau.simpleMessengerApp.service.ServiceProvider;
import by.vironit.training.basumatarau.simpleMessengerApp.validator.IncomingMessageValidator;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(Conversation.class);
    private static Gson gson = new Gson();

    private HttpSession httpSession;
    private User authorizedUser;
    private Map<Long, Session> activeSessions;

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
                = (Map<Long, Session>) httpSession.getServletContext()
                .getAttribute(FrontController.ACTIVE_WSSESSIONS_ATTR_NAME);

        activeSessions.put(authorizedUser.getId(), session);
    }

    @OnMessage
    public void onMessage(Session session, IncomingMessageDto message) throws IOException {
        try {
            final Contact contact = contactService.findContactById(message.getContactId())
                    .orElseThrow(() -> new ControllerException("failed to retrieve contact"));

            IncomingMessageValidator.validate(message);

            if(message.getBody().trim().length() > 0){
                final MessageDto messageDto = messageService.persistMessage(message, contact);

                final Session ownerSession = activeSessions.get(contact.getOwner().getId());
                if(ownerSession!=null){
                    ownerSession.getBasicRemote().sendObject(messageDto);
                }
                final Session personSession = activeSessions.get(contact.getPerson().getId());
                if(personSession!=null){
                    personSession.getBasicRemote().sendObject(messageDto);
                }
            }

        } catch (EncodeException | ServiceException | ControllerException e) {
            logger.error("ws error thrown from within webSocket session opened for user {} : {}", authorizedUser, e);
        } catch (ValidationException e) {
            logger.info("incoming message from {} is too big to be handled...", authorizedUser);
            final WsErrorDto vsErrorDto = new WsErrorDto("invalid message: " + e.getLocalizedMessage());
            activeSessions
                    .get(authorizedUser.getId())
                    .getBasicRemote()
                    .sendText(gson.toJson(vsErrorDto));
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        activeSessions.remove(authorizedUser.getId());
        // WebSocket connection closes
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }
}
