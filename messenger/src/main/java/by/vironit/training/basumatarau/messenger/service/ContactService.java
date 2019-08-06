package by.vironit.training.basumatarau.messenger.service;

import by.vironit.training.basumatarau.messenger.dto.ContactEntryVo;
import by.vironit.training.basumatarau.messenger.dto.UserProfileDto;
import by.vironit.training.basumatarau.messenger.model.PersonalContact;
import by.vironit.training.basumatarau.messenger.model.ContactEntry;
import by.vironit.training.basumatarau.messenger.model.Subscription;
import by.vironit.training.basumatarau.messenger.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ContactService {
    Page<ContactEntryVo> getContactEntriesForUser(UserProfileDto user, Pageable pageable);
    Optional<ContactEntry> findContactEntryById(Long id);
    Optional<PersonalContact> findContactById(Long id);
    Optional<Subscription> findSubscriptionById(Long id);
    void removeContactEntryById(Long entryId);

    void sendContactRequest(UserProfileDto owner, UserProfileDto person) throws InstantiationException;
    void confirmContactRequest(PersonalContact personalContact) throws InstantiationException;
    void declineContactRequest(PersonalContact personalContact);

    Page<ContactEntryVo> getPendingContactsForUser(UserProfileDto userDto, Pageable pageable);
    Optional<PersonalContact> getPersonalContact(User owner, User person);

    ContactEntryVo getContactEntryForUserByEntryId(Long contactEntryId, Long ownerId);
}
