package by.vironit.training.basumatarau.messenger.security.impl;

import by.vironit.training.basumatarau.messenger.model.User;
import by.vironit.training.basumatarau.messenger.security.CustomUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class CustomUserDetailsImpl implements CustomUserDetails {

    private Long id;
    private String registrationId;
    private String email;
    private String passwordHash;
    private boolean isEnabled;

    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public CustomUserDetailsImpl(Long id,
                                 String registrationId,
                                 String passwordHash,
                                 String email,
                                 boolean isEnabled,
                                 Collection<? extends GrantedAuthority> authorities,
                                 Map<String, Object> attributes) {
        this.id = id;
        this.passwordHash = passwordHash;
        this.email = email;
        this.registrationId = registrationId;
        this.isEnabled = isEnabled;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    public static CustomUserDetails produce(User user){
        return produce(user, null);
    }

    public static CustomUserDetails produce(User user, Map<String, Object> attributes){
        return new CustomUserDetailsImpl(
                user.getId(),
                user.getAuthProvider().toString(),
                user.getPasswordHash(),
                user.getEmail(),
                user.getEnabled(),
                getSimpleGrantedAuthorities(user),
                attributes);
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getRegistrationId() {
        return registrationId;
    }

    private static Collection<? extends GrantedAuthority> getSimpleGrantedAuthorities(User user) {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }
    //todo figure out if this is a common practice for identity providers (google etc...) to have short living accounts

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //todo figure out if google can lock an account and share that through api

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //todo implement google api token renewal unless resource owner's token expired

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
