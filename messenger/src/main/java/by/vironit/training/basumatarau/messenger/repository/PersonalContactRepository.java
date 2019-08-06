package by.vironit.training.basumatarau.messenger.repository;

import by.vironit.training.basumatarau.messenger.model.PersonalContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PersonalContactRepository extends JpaRepository<PersonalContact, Long> {

    @Query("select c from PersonalContact c " +
            "where c.owner.id=:ownerId and c.person.id=:personId")
    Optional<PersonalContact> findContactByOwnerIdAndPersonId(
            @Param("ownerId") Long ownerId,
            @Param("personId") Long personId);
}
