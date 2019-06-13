package by.vironit.training.basumatarau.instantMessengerApp.socket;

import by.vironit.training.basumatarau.instantMessengerApp.model.PrivateMessage;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/conversation/{contactId}",
        configurator = GetHttpSessionConfigurator.class)
public class Conversation {

    private Session wsSession;
    private HttpSession httpSession;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws IOException {
        this.wsSession = session;
        this.httpSession = (HttpSession) config.getUserProperties()
                .get(HttpSession.class.getName());

        // Get session and WebSocket connection
    }

    @OnMessage
    public void onMessage(Session session, PrivateMessage message) throws IOException {
        // Handle new messages (echo for a test)
        try {
            wsSession.getBasicRemote().sendObject(message);
        } catch (EncodeException e) {
            //todo something about it...
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        // WebSocket connection closes
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }
}
