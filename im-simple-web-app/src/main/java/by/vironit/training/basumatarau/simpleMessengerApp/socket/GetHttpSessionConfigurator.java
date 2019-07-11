package by.vironit.training.basumatarau.simpleMessengerApp.socket;

import by.vironit.training.basumatarau.simpleMessengerApp.controller.SessionHandler;
import by.vironit.training.basumatarau.simpleMessengerApp.model.User;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator{
    public static final String AUTHORIZED_USER_ATTR_NAME = "isValid";

    @Override
    public void modifyHandshake(ServerEndpointConfig config,
                                HandshakeRequest request,
                                HandshakeResponse response) {
        HttpSession httpSession = (HttpSession)request.getHttpSession();
        config.getUserProperties().put(HttpSession.class.getName(), httpSession);

        final User authorizedUser = SessionHandler.getAuthorizedUser(httpSession);
        if(authorizedUser!=null){
            config.getUserProperties().put(AUTHORIZED_USER_ATTR_NAME, authorizedUser);
        }
    }
}
