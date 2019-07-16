package by.vironit.training.basumatarau.messengerService.repository;

import by.vironit.training.basumatarau.messengerService.model.Contact;
import by.vironit.training.basumatarau.messengerService.model.ContactEntry;
import by.vironit.training.basumatarau.messengerService.model.Subscription;
import by.vironit.training.basumatarau.messengerService.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ContactEntryRepository
        extends JpaRepository<ContactEntry, Long> {

    @Query(value = "select con from ContactEntry con " +
            "where con.owner.id=:#{#user.id} and treat(con as Contact).isConfirmed=true " +
            "or con.owner.id=:#{#user.id} and type(con) in (Subscription) ")
    Page<ContactEntry> getConfirmedContactsForUser(
            @Param("user") User owner,
            Pageable pageable
    );

    @Query(value = "select con from ContactEntry con " +
            //pending contact requests for user's approval
            "where treat(con as Contact).person.id=:#{#user.id} and treat(con as Contact).isConfirmed=false " +
            //pending user's contact requests
            "or con.owner.id=:#{#user.id} and treat(con as Contact).isConfirmed=false ")
    Page<ContactEntry> getPendingContactsForUser(
            @Param("user") User owner,
            Pageable pageable
    );

    Optional<Contact> findContactByOwnerAndPerson(User owner, User person);

    @Query(value = "select ce from ContactEntry ce " +
            "where ce.id=?1 and type(ce) in (Contact) ")
    Optional<Contact> findContactById(Long id);

    @Query(value = "select ce from ContactEntry ce " +
            "where ce.id=?1 and type(ce) in (Subscription) ")
    Optional<Subscription> findSubscriptionById(Long id);
}
