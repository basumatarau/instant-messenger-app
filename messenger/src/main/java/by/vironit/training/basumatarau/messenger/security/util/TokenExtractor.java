package by.vironit.training.basumatarau.messenger.security.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TokenExtractor {

    @Value("${app.auth.tokenPrefix:Bearer }")
    private String tokenPrefix;

    public String extract(String header){

        if(StringUtils.isEmpty(header)){
            throw new AuthenticationServiceException("Authentication header can not be blank");
        }

        if(header.length() < tokenPrefix.length()){
            throw new AuthenticationServiceException("Invalid authentication token length");
        }

        return header.substring(tokenPrefix.length());
    }
}
