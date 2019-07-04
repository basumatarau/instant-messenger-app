package by.vironit.training.basumatarau.instantMessengerApp.repository;

import by.vironit.training.basumatarau.instantMessengerApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query("select u from User u join fetch u.contactEntries where u.email=?1 ")
    User findUserWithContactEntriesByEmail(String email);

    @Query("select u from User u " +
            "join fetch u.contactEntries entry " +
            "where u.email=?1 and type(entry) in (Contact) ")
    User findUserWithContactsByEmail(String email);

    @Query("select u from User u " +
            "join fetch u.contactEntries entry " +
            "where u.email=?1 and type(entry) in (Subscription) ")
    User findUserWithSubscriptionsByEmail(String email);
}
