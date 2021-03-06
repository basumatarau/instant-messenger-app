package by.vironit.training.basumatarau.messenger.security.impl;

import by.vironit.training.basumatarau.messenger.model.User;
import by.vironit.training.basumatarau.messenger.repository.UserRepository;
import by.vironit.training.basumatarau.messenger.security.CustomUserDetails;
import by.vironit.training.basumatarau.messenger.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements CustomUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        final User user = userRepository
                .findByEmail(s)
                .orElseThrow(() -> new UsernameNotFoundException("user name not found: " + s));

        return convertToCustomUserDetails(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomUserDetails> loadUserByUserId(Long id) throws EntityNotFoundException {
        return userRepository
                .findUserById(id)
                .map(this::convertToCustomUserDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomUserDetails> loadUserByEmail(String email) throws EntityNotFoundException {
        return userRepository
                .findByEmail(email)
                .map(this::convertToCustomUserDetails);
    }

    private CustomUserDetails convertToCustomUserDetails(User user) {
        return CustomUserDetailsImpl.produce(user);
    }

    private UserDetails convertToUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPasswordHash())
                .disabled(!user.getEnabled())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .authorities(buildGrantedAuthorities(user))
                .build();
    }

    private GrantedAuthority[] buildGrantedAuthorities(User user) {
        return new GrantedAuthority[]{new SimpleGrantedAuthority(user.getRole().toString())};
    }
}
