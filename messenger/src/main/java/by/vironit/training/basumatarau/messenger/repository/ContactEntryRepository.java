package by.vironit.training.basumatarau.messenger.repository;

import by.vironit.training.basumatarau.messenger.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ContactEntryRepository
        extends JpaRepository<ContactEntry, Long> {

    @Query(value = "select con from ContactEntry con " +
            "where con.owner.id=:#{#user.id} " +
            "and (type(con) in (PersonalContact) and con.isConfirmed=true " +
            "or type(con) in (Subscription)) ")
    Page<ContactEntry> getConfirmedContactsForUser(
            @Param("user") User owner,
            Pageable pageable
    );

    @Query(value = "select con from ContactEntry con " +
            "where con.id=:#{#contractId} and con.owner.id=:#{#ownerId} " +
            "and (type(con) in (PersonalContact) and con.isConfirmed=true " +
            "or " +
            "type(con) in (Subscription)) ")
    Optional<ContactEntry> findContactEntryByContactIdAndOwnerId(
            @Param("contractId") Long contactId,
            @Param("ownerId") Long ownerId
    );

    //todo fix the shit:
    @Query(value = "select con from ContactEntry con " +
            //pending contact requests for user's approval
            "where treat(con as PersonalContact).person.id=:#{#user.id} and treat(con as PersonalContact).isConfirmed=false " +
            //pending user's contact requests
            "or con.owner.id=:#{#user.id} and treat(con as PersonalContact).isConfirmed=false ")
    Page<ContactEntry> getPendingContactsForUser(
            @Param("user") User owner,
            Pageable pageable
    );

    Optional<PersonalContact> findContactByOwnerAndPerson(User owner, User person);

    @Query(value = "select ce from ContactEntry ce " +
            "where ce.id=?1 and type(ce) in (PersonalContact) ")
    Optional<PersonalContact> findContactById(Long id);

    @Query(value = "select ce from ContactEntry ce " +
            "where ce.id=?1 and type(ce) in (Subscription) ")
    Optional<Subscription> findSubscriptionById(Long id);

    @Query("select c from ContactEntry c " +
            "where treat(c as PersonalContact).owner.id=:ownerId " +
            "and " +
            "treat(c as PersonalContact).person.id=:personId")
    Optional<PersonalContact> findContactByOwnerIdAndPersonId(
            @Param("ownerId") Long ownerId,
            @Param("personId") Long personId);

    @Query(value = "select c from ContactEntry c " +
            "where treat(c as PersonalContact).id=:id")
    Optional<PersonalContact> findPersonalContactById(
            @Param("id") Long id
    );

    @Query(value = "select con from ContactEntry con " +
            "where type(con) in (Subscription) " +
            "and " +
            "treat(con as Subscription).owner.id=:#{#owner.id} " +
            "and " +
            "treat(con as Subscription).chatRoom.id=:#{#chatRoom.id} ")
    Optional<Subscription> findSubscriptionByChatRoomAndOwner(
            @Param("chatRoom") ChatRoom chatRoom,
            @Param("owner") User owner
    );

}
