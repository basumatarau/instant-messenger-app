package by.vironit.training.basumatarau.messengerService.repository;

import by.vironit.training.basumatarau.messengerService.model.ContactEntry;
import by.vironit.training.basumatarau.messengerService.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactEntryRepository
        extends JpaRepository<ContactEntry, Long> {

    Slice<ContactEntry> getAllContactsByOwner(User owner, Pageable pageable);
}
