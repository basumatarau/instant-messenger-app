package by.vironit.training.basumatarau.messengerService.service.impl;

import by.vironit.training.basumatarau.messengerService.dto.ContactEntryVo;
import by.vironit.training.basumatarau.messengerService.dto.UserProfileDto;
import by.vironit.training.basumatarau.messengerService.exception.NoEntityFound;
import by.vironit.training.basumatarau.messengerService.model.Contact;
import by.vironit.training.basumatarau.messengerService.model.ContactEntry;
import by.vironit.training.basumatarau.messengerService.model.Subscription;
import by.vironit.training.basumatarau.messengerService.model.User;
import by.vironit.training.basumatarau.messengerService.repository.ContactEntryRepository;
import by.vironit.training.basumatarau.messengerService.repository.UserRepository;
import by.vironit.training.basumatarau.messengerService.service.ContactEntryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContactEntryServiceImpl implements ContactEntryService {

    @Autowired
    private ContactEntryRepository contactEntryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

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
    public Optional<ContactEntry> findContactEntryById(Long id) {
        return contactEntryRepository.findById(id);
    }

    @Override
    public void removeContactEntryById(Long entryId) {
        final ContactEntry contactEntry =
                contactEntryRepository
                        .findById(entryId)
                        .orElseThrow(() -> new NoEntityFound("contact not found"));

        if(contactEntry instanceof Contact){
            final Contact contact = (Contact) contactEntry;
            if(contact.getIsConfirmed()){
                final Contact counterContact = contactEntryRepository
                        .findContactByOwnerAndPerson(contact.getPerson(), contact.getOwner())
                        .orElseThrow(() -> new NoEntityFound("counterContact not found"));
                contactEntryRepository.delete(counterContact);
            }
        }
        contactEntryRepository.delete(contactEntry);
    }

    @Override
    public void sendContactRequest(UserProfileDto owner, UserProfileDto person)
            throws InstantiationException {

        final Contact newUnconfirmedContact = new Contact.ContactBuilder()
                .owner(modelMapper.map(owner, User.class))
                .person(modelMapper.map(person, User.class))
                .confirmed(false)
                .build();

        contactEntryRepository.save(newUnconfirmedContact);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Contact> findContactById(Long id) {
        return contactEntryRepository.findContactById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Subscription> findSubscriptionById(Long id) {
        return contactEntryRepository.findSubscriptionById(id);
    }

    @Override
    @Transactional
    public void confirmContactRequest(Contact contact) throws InstantiationException {
        final Contact counterContact = new Contact.ContactBuilder()
                .owner(contact.getPerson())
                .person(contact.getOwner())
                .confirmed(true)
                .build();

        contact.setIsConfirmed(true);

        contactEntryRepository.save(counterContact);
        contactEntryRepository.save(contact);
    }

    @Override
    public void declineContactRequest(Contact contact) {
        contactEntryRepository.delete(contact);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Contact> getPersonalContact(User owner, User person) {
        return contactEntryRepository.findContactByOwnerAndPerson(owner, person);
    }
}
