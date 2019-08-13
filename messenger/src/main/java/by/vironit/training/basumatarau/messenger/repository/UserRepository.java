package by.vironit.training.basumatarau.messenger.repository;

import by.vironit.training.basumatarau.messenger.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>{

    Optional<User> findByEmail(String email);

    @Query("select u from User u left join fetch u.contactEntries where u.email=?1 ")
    Optional<User> findUserWithContactEntriesByEmail(String email);

    @Query("select u from User u " +
            "left join fetch u.contactEntries entry " +
            "where u.email=?1 and type(entry) in (PersonalContact) ")
    Optional<User> findUserWithPersonalContactsByEmail(String email);

    @Query("select u from User u " +
            "left join fetch u.contactEntries entry " +
            "where u.email=?1 and type(entry) in (Subscription) ")
    Optional<User> findUserWithSubscriptionsByEmail(String email);

    @Override
    Page<User> findAll(Specification<User> spec, Pageable pageable);
}
