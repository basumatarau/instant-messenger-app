package by.vironit.training.basumatarau.messenger.security;

import by.vironit.training.basumatarau.messenger.security.impl.CustomUserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtTokenProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.auth.tokenPrefix:Bearer }")
    private String tokenPrefix;

    @Value("${app.auth.tokenExpirationMsec}")
    private Long tokenExpirationPeriod;

    private Key secureKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Autowired
    private ModelMapper modelMapper;

    public String createToken(Authentication auth){
        CustomUserDetailsImpl principal = (CustomUserDetailsImpl) auth.getPrincipal();
        Date expiryDate = new Date(new Date().getTime() + tokenExpirationPeriod);

        //todo replace the deprecated token signature method
        return Jwts.builder()
                //getName method of the customized user principal returns the user id
                .setSubject(principal.getName())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(secureKey)
                .compact();
    }

    public Long getUserIdFromToken(String token){
        Claims body = Jwts.parser()
                .setSigningKey(secureKey)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(body.getSubject());
    }

    public boolean validate(String token){
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secureKey).parseClaimsJws(token.replace(tokenPrefix, ""));
            return true;
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
        return false;
    }
}
