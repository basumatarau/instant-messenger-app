package by.vironit.training.basumatarau.messengerService.service.impl;

import by.vironit.training.basumatarau.messengerService.dto.ContactEntryVo;
import by.vironit.training.basumatarau.messengerService.dto.UserProfileDto;
import by.vironit.training.basumatarau.messengerService.model.Contact;
import by.vironit.training.basumatarau.messengerService.model.ContactEntry;
import by.vironit.training.basumatarau.messengerService.model.User;
import by.vironit.training.basumatarau.messengerService.repository.ContactEntryRepository;
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
public class ContactEntryServiceImpl implements ContactEntryService {

    @Autowired
    private ContactEntryRepository contactEntryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ContactEntryVo> getContactEntriesForUser(UserProfileDto userDto, Pageable pageable) {
        final User user = modelMapper.map(userDto, User.class);

        final Slice<ContactEntry> allContactsByOwner =
                contactEntryRepository.getAllContactsByOwner(user, pageable);

        final List<ContactEntryVo> contacts = allContactsByOwner.getContent()
                .stream()
                .map(c -> modelMapper.map(c, ContactEntryVo.class))
                .collect(Collectors.toList());

        return new PageImpl<>(contacts, pageable, contacts.size());
    }

    @Override
    public void removeContactEntry(ContactEntry contactEntry) {

    }

    @Override
    public void sendContactRequest(User owner, User person) {

    }

    @Override
    public void confirmContactRequest(Contact contact) {

    }

    @Override
    public void declineContactRequest(Contact contact) {

    }

    @Override
    public Optional<ContactEntry> findContactById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Contact> getPersonalContact(User owner, User person) {
        return Optional.empty();
    }
}
