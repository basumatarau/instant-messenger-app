package by.vironit.training.basumatarau.instantMessengerApp.socket;

import by.vironit.training.basumatarau.instantMessengerApp.controller.SessionHandler;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator{
    public static final String IS_VALID = "isValid";

    @Override
    public void modifyHandshake(ServerEndpointConfig config,
                                HandshakeRequest request,
                                HandshakeResponse response) {
        HttpSession httpSession = (HttpSession)request.getHttpSession();
        config.getUserProperties().put(HttpSession.class.getName(), httpSession);

        final User authorizedUser = SessionHandler.getAuthorizedUser(httpSession);
        if(authorizedUser!=null){
            config.getUserProperties().put(IS_VALID, Boolean.TRUE);
        }

        final ServletContext servletContext = httpSession.getServletContext();
        config.getUserProperties().put(ServletContext.class.getName(), servletContext);
    }
}
