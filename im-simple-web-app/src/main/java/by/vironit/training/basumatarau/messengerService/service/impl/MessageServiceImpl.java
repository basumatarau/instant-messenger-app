package by.vironit.training.basumatarau.messengerService.service.impl;

import by.vironit.training.basumatarau.messengerService.dao.ContactDao;
import by.vironit.training.basumatarau.messengerService.dao.DaoProvider;
import by.vironit.training.basumatarau.messengerService.dao.MessageDao;
import by.vironit.training.basumatarau.messengerService.dto.ContactVo;
import by.vironit.training.basumatarau.messengerService.dto.IncomingMessageDto;
import by.vironit.training.basumatarau.messengerService.dto.MessageDto;
import by.vironit.training.basumatarau.messengerService.exception.DaoException;
import by.vironit.training.basumatarau.messengerService.exception.ServiceException;
import by.vironit.training.basumatarau.messengerService.model.Contact;
import by.vironit.training.basumatarau.messengerService.model.PrivateMessage;
import by.vironit.training.basumatarau.messengerService.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

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
            logger.warn("failed to convert incoming message to pojo: " + incomingMessage);
            throw new ServiceException("failed to compose a message", e);
        }

        try {
            messageDao.save(message);
        } catch (DaoException e) {
            logger.error("failed to save message: " + message);
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

            logger.error("failed to fetch contact by id: " + contactDto.getId());
            throw new ServiceException("failed to fetch contact", e);
        }

        if(personContact.isPresent()){
            final Contact ownContact = ownerContact.get();
            final Contact perContact = personContact.get();
            try {
                messages = messageDao.getMessagesForContact(ownContact);
                messages.addAll(messageDao.getMessagesForContact(perContact));
            } catch (DaoException e) {

                logger.error("failed to fetch messages for contact: " + ownContact);
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
