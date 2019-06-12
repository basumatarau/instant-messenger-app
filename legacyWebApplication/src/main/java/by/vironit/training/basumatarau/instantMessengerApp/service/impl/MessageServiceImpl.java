package by.vironit.training.basumatarau.instantMessengerApp.service.impl;

import by.vironit.training.basumatarau.instantMessengerApp.dao.ContactDao;
import by.vironit.training.basumatarau.instantMessengerApp.dao.DaoProvider;
import by.vironit.training.basumatarau.instantMessengerApp.dao.MessageDao;
import by.vironit.training.basumatarau.instantMessengerApp.dao.UserDao;
import by.vironit.training.basumatarau.instantMessengerApp.dto.ContactDto;
import by.vironit.training.basumatarau.instantMessengerApp.dto.MessageDto;
import by.vironit.training.basumatarau.instantMessengerApp.exception.DaoException;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ServiceException;
import by.vironit.training.basumatarau.instantMessengerApp.model.Contact;
import by.vironit.training.basumatarau.instantMessengerApp.model.PrivateMessage;
import by.vironit.training.basumatarau.instantMessengerApp.service.MessageService;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MessageServiceImpl implements MessageService {
    private final ContactDao contactDao = DaoProvider.DAO.contactDao;
    private final MessageDao messageDao = DaoProvider.DAO.messageDao;

    @Override
    public List<MessageDto> getMessagesForContact(ContactDto contactDto)
            throws ServiceException {
        List<PrivateMessage> messages = new LinkedList<>();
        final Optional<Contact> foundContact;
        try {
            foundContact = contactDao.findById(contactDto.getId());
        } catch (DaoException e) {
            throw new ServiceException("failed to fetch contact", e);
        }

        if(foundContact.isPresent()){
            final Contact contact = foundContact.get();
            try {
                messages = messageDao.getMessagesForContact(contact);
            } catch (DaoException e) {
                throw new ServiceException("failed to fetch messgaes for contact", e);
            }
        }
        return messages
                .stream()
                .map(MessageDto::getDto)
                .collect(Collectors.toList());
    }
}
