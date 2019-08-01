package by.vironit.training.basumatarau.messenger.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ConnectMessageInterceptor implements ChannelInterceptor {
    private static final Logger log = LoggerFactory.getLogger(ConnectMessageInterceptor.class);

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if(StompCommand.CONNECT.equals(Objects.requireNonNull(accessor).getCommand())){
            final String token = (String) message.getHeaders().get("Authorization");

            if (StringUtils.isNotEmpty(token) && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
                try {
                    final byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

                    final Jws<Claims> parsedToken = Jwts.parser()
                            .setSigningKey(signingKey)
                            .parseClaimsJws(token.replace("Bearer ", ""));

                    final String username = parsedToken
                            .getBody()
                            .getSubject();

                    final List<SimpleGrantedAuthority> authorities =
                            ((List<?>) parsedToken.getBody()
                                    .get("rol")).stream()
                                    .map(authority -> new SimpleGrantedAuthority((String) authority))
                                    .collect(Collectors.toList());

                    if (StringUtils.isNotEmpty(username)) {
                        final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(username, null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                } catch (ExpiredJwtException exception) {
                    log.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
                } catch (UnsupportedJwtException exception) {
                    log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
                } catch (MalformedJwtException exception) {
                    log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
                } catch (SignatureException exception) {
                    log.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
                } catch (IllegalArgumentException exception) {
                    log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
                }
            }

        }

        return message;
    }

}
