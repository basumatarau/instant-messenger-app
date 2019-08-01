package by.vironit.training.basumatarau.messenger.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

import static org.springframework.messaging.simp.SimpMessageType.*;

@Configuration
public class WebSocketSecurityConfig
        extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .nullDestMatcher().authenticated()
                .simpSubscribeDestMatchers("/user/queue/errors", "/topic/greetings").permitAll()
                .simpDestMatchers("/app/hello").permitAll()
                .simpDestMatchers("/app/**").hasAnyRole("USER", "ADMIN")
                .simpSubscribeDestMatchers("/user/**", "/topic/**").hasAnyRole("USER", "ADMIN")
                //.simpTypeMatchers(MESSAGE, SUBSCRIBE).denyAll()
                .anyMessage().denyAll()
                ;
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
