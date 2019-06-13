package by.vironit.training.basumatarau.instantMessengerApp.service.impl;

import by.vironit.training.basumatarau.instantMessengerApp.dao.ContactDao;
import by.vironit.training.basumatarau.instantMessengerApp.dao.DaoProvider;
import by.vironit.training.basumatarau.instantMessengerApp.dao.MessageDao;
import by.vironit.training.basumatarau.instantMessengerApp.dto.ContactDto;
import by.vironit.training.basumatarau.instantMessengerApp.dto.MessageDto;
import by.vironit.training.basumatarau.instantMessengerApp.exception.DaoException;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ServiceException;
import by.vironit.training.basumatarau.instantMessengerApp.model.Contact;
import by.vironit.training.basumatarau.instantMessengerApp.model.PrivateMessage;
import by.vironit.training.basumatarau.instantMessengerApp.service.MessageService;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MessageServiceImpl implements MessageService {
    private final ContactDao contactDao;
    private final MessageDao messageDao;
    {
        contactDao = DaoProvider.DAO.contactDao;
        messageDao = DaoProvider.DAO.messageDao;
    }

    @Override
    public List<MessageDto> getMessagesForContact(ContactDto contactDto)
            throws ServiceException {
        List<PrivateMessage> messages = new LinkedList<>();
        final Optional<Contact> ownerContact;
        final Optional<Contact> personContact;
        try {
            ownerContact = contactDao.findById(contactDto.getId());
            if (ownerContact.isPresent()) {
                personContact = contactDao.getContactsFoOwnerAndPerson(
                        ownerContact.get().getPerson(),
                        ownerContact.get().getOwner());
            }else throw new DaoException("failed to fetch contacts");
        } catch (DaoException e) {
            throw new ServiceException("failed to fetch contact", e);
        }

        if(personContact.isPresent()){
            final Contact ownContact = ownerContact.get();
            final Contact perContact = personContact.get();
            try {
                messages = messageDao.getMessagesForContact(ownContact);
                messages.addAll(messageDao.getMessagesForContact(perContact));
            } catch (DaoException e) {
                throw new ServiceException("failed to fetch messgaes for contact", e);
            }
        }
        return messages
                .stream()
                .map(MessageDto::getDto)
                .sorted(Comparator.comparing(MessageDto::getTimesent))
                .collect(Collectors.toList());
    }
}
