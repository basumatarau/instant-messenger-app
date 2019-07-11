package by.vironit.training.basumatarau.simpleMessengerApp.service;

import by.vironit.training.basumatarau.simpleMessengerApp.dto.ContactVo;
import by.vironit.training.basumatarau.simpleMessengerApp.dto.MessageDto;
import by.vironit.training.basumatarau.simpleMessengerApp.exception.ServiceException;
import by.vironit.training.basumatarau.simpleMessengerApp.model.Contact;
import by.vironit.training.basumatarau.simpleMessengerApp.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ContactService {
    List<ContactVo> findAllContactsForUser(User user) throws ServiceException;
    void removeContact(Contact contact) throws ServiceException;
    void sendContactRequestToUser (User owner, User person) throws ServiceException;
    void confirmContactRequest(Contact contact) throws ServiceException;
    void declineContactRequest(Contact contact) throws ServiceException;
    Optional<Contact> findContactById(Long id) throws ServiceException;

    Optional<Contact> getContactByOwnerAndUser(User owner, User authorizedUser) throws ServiceException;
    void removeContactForOwnerAndUser(User owner, User person) throws ServiceException;
    Map<ContactVo, MessageDto> getContactsAndLastMessages(User owner) throws ServiceException;
}
