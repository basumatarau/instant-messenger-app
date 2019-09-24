package by.vironit.training.basumatarau.messenger.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public interface CustomUserDetailsService extends UserDetailsService {

    @Transactional(readOnly = true)
    Optional<CustomUserDetails> loadUserByUserId(Long id) throws EntityNotFoundException;

    @Transactional(readOnly = true)
    Optional<CustomUserDetails> loadUserByEmail(String email) throws EntityNotFoundException;
}
