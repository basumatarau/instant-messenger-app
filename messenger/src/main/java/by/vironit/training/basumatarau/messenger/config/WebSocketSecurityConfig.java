package by.vironit.training.basumatarau.messenger.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig
        extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .nullDestMatcher().authenticated()
                .simpSubscribeDestMatchers("/user/queue/errors", "/topic/greetings").permitAll()
                .simpDestMatchers("/app/hello").permitAll()
                .simpDestMatchers("/app/**").hasAnyAuthority("USER", "ADMIN")
                .simpSubscribeDestMatchers("/user/**", "/topic/**").hasAnyAuthority("USER", "ADMIN")
                .anyMessage().denyAll();
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
