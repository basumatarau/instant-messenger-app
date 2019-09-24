package by.vironit.training.basumatarau.messenger.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;

public interface CustomUserDetails extends UserDetails, OAuth2User, Serializable {
    String getEmail();
    String getRegistrationId();
}
