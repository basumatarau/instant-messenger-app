package by.vironit.training.basumatarau.messenger.config;

import by.vironit.training.basumatarau.messenger.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;


@Component
public class SubscribeMessageInterceptor implements ChannelInterceptor {

    private final Logger log = LoggerFactory.getLogger(SubscribeMessageInterceptor.class);

    @Autowired
    private UserService userService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        log.warn("in preSend of SubscribeMessageInterceptor" + message.getHeaders().toString());

        return message;
    }

}
