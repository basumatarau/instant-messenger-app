package by.vironit.training.basumatarau.messenger.config;

import by.vironit.training.basumatarau.messenger.security.CustomUserDetails;
import by.vironit.training.basumatarau.messenger.security.CustomUserDetailsService;
import by.vironit.training.basumatarau.messenger.security.JwtTokenProvider;
import by.vironit.training.basumatarau.messenger.security.util.TokenExtractor;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ConnectMessageInterceptor implements ChannelInterceptor {
    private static final Logger log = LoggerFactory.getLogger(ConnectMessageInterceptor.class);

    @Value("{app.auth.headerName:Authorization}")
    private String jwtTokenHeaderName;

    @Autowired
    private TokenExtractor tokenExtractor;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if(StompCommand.CONNECT.equals(Objects.requireNonNull(accessor).getCommand())){

            String rawToken = tokenExtractor.extract(((String) message.getHeaders().get(jwtTokenHeaderName)));

            if(jwtTokenProvider.validate(rawToken)){
                Long userId = jwtTokenProvider.getUserIdFromToken(rawToken);

                CustomUserDetails customUserDetails =
                        customUserDetailsService
                                .loadUserByUserId(userId)
                                .orElseThrow(() ->  new EntityNotFoundException("no user found by id: " + userId));

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                customUserDetails,
                                null, customUserDetails.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        return message;
    }

}
