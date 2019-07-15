package by.vironit.training.basumatarau.messengerService.service;

import by.vironit.training.basumatarau.messengerService.dto.ContactEntryVo;
import by.vironit.training.basumatarau.messengerService.dto.UserProfileDto;
import by.vironit.training.basumatarau.messengerService.model.Contact;
import by.vironit.training.basumatarau.messengerService.model.ContactEntry;
import by.vironit.training.basumatarau.messengerService.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface ContactEntryService {
    Page<ContactEntryVo> getContactEntriesForUser(UserProfileDto user, Pageable pageable);
    void removeContactEntry(ContactEntry contactEntry);
    void sendContactRequest(User owner, User person);
    void confirmContactRequest(Contact contact);
    void declineContactRequest(Contact contact);
    Optional<ContactEntry> findContactById(Long id);
    Optional<Contact> getPersonalContact(User owner, User person);

}
