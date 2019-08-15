package by.vironit.training.basumatarau.messenger.config;

import by.vironit.training.basumatarau.messenger.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

public class OutboundMessageInterceptor implements ChannelInterceptor {

    private final Logger log = LoggerFactory.getLogger(OutboundMessageInterceptor.class);

    @Autowired
    private UserService userService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        log.warn("############ in preSend " + message.getHeaders().toString());

/*        if(StompCommand.SUBSCRIBE.equals(Objects.requireNonNull(accessor).getCommand())){
            final Principal principal = accessor.getUser();

            //todo: "user online" status update notification can be implemented within the interceptor
            //postSend ?
        }*/
        return message;
    }

    @Override
    public void afterSendCompletion(Message<?> message,
                                    MessageChannel channel,
                                    boolean sent,
                                    Exception ex) {
        log.warn("############ in afterSendCompletion " + message.getHeaders().toString());

    }
}
