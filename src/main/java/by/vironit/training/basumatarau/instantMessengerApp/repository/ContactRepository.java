package by.vironit.training.basumatarau.instantMessengerApp.repository;

import by.vironit.training.basumatarau.instantMessengerApp.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("select c from Contact c " +
            "where c.owner.id=:ownerId and c.person.id=:personId")
    Optional<Contact> findContactByOwnerIdAndPersonId(
            @Param("ownerId") Long ownerId,
            @Param("personId") Long personId);
}
