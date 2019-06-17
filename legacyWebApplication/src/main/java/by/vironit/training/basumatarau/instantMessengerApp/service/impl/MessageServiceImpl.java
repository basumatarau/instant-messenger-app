package by.vironit.training.basumatarau.instantMessengerApp.service.impl;

import by.vironit.training.basumatarau.instantMessengerApp.dao.ContactDao;
import by.vironit.training.basumatarau.instantMessengerApp.dao.DaoProvider;
import by.vironit.training.basumatarau.instantMessengerApp.dao.MessageDao;
import by.vironit.training.basumatarau.instantMessengerApp.dto.ContactVo;
import by.vironit.training.basumatarau.instantMessengerApp.dto.IncomingMessageDto;
import by.vironit.training.basumatarau.instantMessengerApp.dto.MessageDto;
import by.vironit.training.basumatarau.instantMessengerApp.exception.DaoException;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ServiceException;
import by.vironit.training.basumatarau.instantMessengerApp.model.Contact;
import by.vironit.training.basumatarau.instantMessengerApp.model.PrivateMessage;
import by.vironit.training.basumatarau.instantMessengerApp.service.MessageService;

import java.util.*;
import java.util.stream.Collectors;

public class MessageServiceImpl implements MessageService {
    private final ContactDao contactDao;
    private final MessageDao messageDao;
    {
        contactDao = DaoProvider.DAO.contactDao;
        messageDao = DaoProvider.DAO.messageDao;
    }

    @Override
    public MessageDto persistMessage(IncomingMessageDto incomingMessage, Contact contact) throws ServiceException {

        final PrivateMessage message;
        try {
            message = new PrivateMessage.PrivateMessageBuilder()
                    .contact(contact)
                    .author(contact.getOwner())
                    .body(incomingMessage.getBody())
                    .timeSent(new Date())
                    .build();

        } catch (InstantiationException e) {
            throw new ServiceException("failed to compose a message", e);
        }

        try {
            messageDao.save(message);
        } catch (DaoException e) {
            throw new ServiceException("failed to send a message", e);
        }

        return MessageDto.getDto(message);
    }

    @Override
    public List<MessageDto> getMessagesForContact(ContactVo contactDto)
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
