package by.vironit.training.basumatarau.messenger.security;

import by.vironit.training.basumatarau.messenger.security.util.TokenExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.persistence.EntityNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    @Value("${app.auth.headerName:Authorization}")
    private String jwtTokenHeaderName;

    @Autowired
    private TokenExtractor tokenExtractor;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private Logger logger = LoggerFactory.getLogger(JwtTokenAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = httpServletRequest.getHeader(jwtTokenHeaderName);

        if(!StringUtils.isEmpty(authHeader)){
            String rawToken = tokenExtractor.extract(authHeader);

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

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
