package by.vironit.training.basumatarau.messenger.service.impl;

import by.vironit.training.basumatarau.messenger.dto.ContactEntryVo;
import by.vironit.training.basumatarau.messenger.dto.UserProfileDto;
import by.vironit.training.basumatarau.messenger.exception.ContactRequestIsAlreadyPending;
import by.vironit.training.basumatarau.messenger.exception.NoEntityFound;
import by.vironit.training.basumatarau.messenger.model.PersonalContact;
import by.vironit.training.basumatarau.messenger.model.ContactEntry;
import by.vironit.training.basumatarau.messenger.model.Subscription;
import by.vironit.training.basumatarau.messenger.model.User;
import by.vironit.training.basumatarau.messenger.repository.ContactEntryRepository;
import by.vironit.training.basumatarau.messenger.repository.UserRepository;
import by.vironit.training.basumatarau.messenger.service.ContactService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactEntryRepository contactEntryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public ContactEntryVo getContactEntryForUserByEntryId(Long contactEntryId, Long ownerId) {
        final ContactEntry contactEntry = contactEntryRepository.findContactEntryByContactIdAndOwnerId(contactEntryId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException("no contact entry has been found"));

        return modelMapper.map(contactEntry, ContactEntryVo.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactEntryVo> getContactEntriesForUser(UserProfileDto userDto, Pageable pageable) {
        final User user = modelMapper.map(userDto, User.class);

        final Page<ContactEntry> allContactsByOwner =
                contactEntryRepository.getConfirmedContactsForUser(user, pageable);

        final List<ContactEntryVo> contacts = allContactsByOwner.getContent()
                .stream()
                .map(c -> modelMapper.map(c, ContactEntryVo.class))
                .collect(Collectors.toList());

        return new PageImpl<>(contacts, pageable, contacts.size());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactEntryVo> getPendingContactsForUser(UserProfileDto userDto, Pageable pageable) {
        final User user = modelMapper.map(userDto, User.class);

        final Page<ContactEntry> allContactsByOwner =
                contactEntryRepository.getPendingContactsForUser(user, pageable);

        final List<ContactEntryVo> contacts = allContactsByOwner.getContent()
                .stream()
                .map(c -> modelMapper.map(c, ContactEntryVo.class))
                .collect(Collectors.toList());

        return new PageImpl<>(contacts, pageable, contacts.size());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContactEntry> findContactEntryById(Long id) {
        return contactEntryRepository.findById(id);
    }

    @Override
    public void removeContactEntryById(Long entryId) {
        final ContactEntry contactEntry =
                contactEntryRepository
                        .findById(entryId)
                        .orElseThrow(() -> new NoEntityFound("contact not found"));

        if(contactEntry instanceof PersonalContact){
            final PersonalContact personalContact = (PersonalContact) contactEntry;
            if(personalContact.getIsConfirmed()){
                final PersonalContact counterPersonalContact = contactEntryRepository
                        .findContactByOwnerAndPerson(personalContact.getPerson(), personalContact.getOwner())
                        .orElseThrow(() -> new NoEntityFound("counterContact not found"));
                contactEntryRepository.delete(counterPersonalContact);
            }
        }
        contactEntryRepository.delete(contactEntry);
    }

    @Override
    public void sendContactRequest(UserProfileDto owner, UserProfileDto person)
            throws InstantiationException {

        final Optional<PersonalContact> contactByOwnerAndPerson = contactEntryRepository.findContactByOwnerAndPerson(
                modelMapper.map(owner, User.class), modelMapper.map(person, User.class));

        if(contactByOwnerAndPerson.isPresent()){
            throw new ContactRequestIsAlreadyPending("contact is already pending");
        }

        final PersonalContact newUnconfirmedPersonalContact = new PersonalContact.ContactBuilder()
                .owner(modelMapper.map(owner, User.class))
                .person(modelMapper.map(person, User.class))
                .confirmed(false)
                .build();

        contactEntryRepository.save(newUnconfirmedPersonalContact);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonalContact> findContactById(Long id) {
        return contactEntryRepository.findContactById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Subscription> findSubscriptionById(Long id) {
        return contactEntryRepository.findSubscriptionById(id);
    }

    @Override
    @Transactional
    public void confirmContactRequest(PersonalContact personalContact) throws InstantiationException {
        final PersonalContact counterPersonalContact = new PersonalContact.ContactBuilder()
                .owner(personalContact.getPerson())
                .person(personalContact.getOwner())
                .confirmed(true)
                .build();

        personalContact.setIsConfirmed(true);

        contactEntryRepository.save(counterPersonalContact);
        contactEntryRepository.save(personalContact);
    }

    @Override
    public void declineContactRequest(PersonalContact personalContact) {
        contactEntryRepository.delete(personalContact);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonalContact> getPersonalContact(User owner, User person) {
        return contactEntryRepository.findContactByOwnerAndPerson(owner, person);
    }
}
