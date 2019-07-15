package by.vironit.training.basumatarau.messengerService.repository;

import by.vironit.training.basumatarau.messengerService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("select u from User u left join fetch u.contactEntries where u.email=?1 ")
    Optional<User> findUserWithContactEntriesByEmail(String email);

    @Query("select u from User u " +
            "left join fetch u.contactEntries entry " +
            "where u.email=?1 and type(entry) in (Contact) ")
    Optional<User> findUserWithContactsByEmail(String email);

    @Query("select u from User u " +
            "left join fetch u.contactEntries entry " +
            "where u.email=?1 and type(entry) in (Subscription) ")
    Optional<User> findUserWithSubscriptionsByEmail(String email);
}
