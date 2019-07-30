package by.vironit.training.basumatarau.messenger.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;

import java.util.Objects;

public class CustomSocketHandShakeInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if(StompCommand.CONNECT.equals(Objects.requireNonNull(accessor).getCommand())){
            final Object authorization = message.getHeaders().get("Authorization");

            //todo: stomp over websocket auth authentication
            Authentication authentication = (Authentication) null;

            accessor.setUser(authentication);
        }

        return message;
    }

}
