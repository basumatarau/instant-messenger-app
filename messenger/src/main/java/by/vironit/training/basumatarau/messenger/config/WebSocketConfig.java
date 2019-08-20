package by.vironit.training.basumatarau.messenger.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final Logger log = LoggerFactory.getLogger(WebSocketMessageBrokerConfigurer.class);

    //todo: partial messaging to be implemented (increased buffer size hogs memory)
    @Bean
    public ServletServerContainerFactoryBean createServletServerContainerFactoryBean() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(32768);
        container.setMaxBinaryMessageBufferSize(32768);
        log.info("Websocket factory returned");
        return container;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint( "/api/WSUpgrade")
                .withSockJS()
                .setStreamBytesLimit(5 * 1024 * 1024)
                .setHttpMessageCacheSize(5 * 1024 * 1024)
                .setDisconnectDelay(30 * 1000);;
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
