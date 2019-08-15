package by.vironit.training.basumatarau.messenger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/WSUpgrade")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration
                .interceptors(getConnectMessageInterceptorBean())
                .interceptors(getSubscribeMessageInterceptorBean());
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration
                .interceptors(getOutboundMessageInterceptor());
    }

    @Bean
    public OutboundMessageInterceptor getOutboundMessageInterceptor(){
        return new OutboundMessageInterceptor();
    }

    @Bean
    public SubscribeMessageInterceptor getSubscribeMessageInterceptorBean(){

        return new SubscribeMessageInterceptor();
    }

    @Bean
    public ConnectMessageInterceptor getConnectMessageInterceptorBean(){

        return new ConnectMessageInterceptor();
    }
}
