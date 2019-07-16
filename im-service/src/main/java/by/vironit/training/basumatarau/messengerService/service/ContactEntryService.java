package by.vironit.training.basumatarau.messengerService.service;

import by.vironit.training.basumatarau.messengerService.dto.ContactEntryVo;
import by.vironit.training.basumatarau.messengerService.dto.UserProfileDto;
import by.vironit.training.basumatarau.messengerService.model.Contact;
import by.vironit.training.basumatarau.messengerService.model.ContactEntry;
import by.vironit.training.basumatarau.messengerService.model.Subscription;
import by.vironit.training.basumatarau.messengerService.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ContactEntryService {
    Page<ContactEntryVo> getContactEntriesForUser(UserProfileDto user, Pageable pageable);
    Optional<ContactEntry> findContactEntryById(Long id);
    Optional<Contact> findContactById(Long id);
    Optional<Subscription> findSubscriptionById(Long id);
    void removeContactEntryById(Long entryId);

    void sendContactRequest(UserProfileDto owner, UserProfileDto person)
            throws InstantiationException;
    void confirmContactRequest(Contact contact) throws InstantiationException;
    void declineContactRequest(Contact contact);

    Optional<Contact> getPersonalContact(User owner, User person);
}
