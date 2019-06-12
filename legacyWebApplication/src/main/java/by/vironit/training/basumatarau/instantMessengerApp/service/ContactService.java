package by.vironit.training.basumatarau.instantMessengerApp.service;

import by.vironit.training.basumatarau.instantMessengerApp.dto.ContactDto;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ServiceException;
import by.vironit.training.basumatarau.instantMessengerApp.model.Contact;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    List<ContactDto> findAllContactsForUser(User user) throws ServiceException;
    void removeContact(Contact contact) throws ServiceException;
    void sendContactRequestToUser (User owner, User person) throws ServiceException;
    void confirmContactRequest(Contact contact) throws ServiceException;
    void declineContactRequest(Contact contact) throws ServiceException;
    Optional<Contact> findContactById(Long id) throws ServiceException;

    Optional<Contact> getContactByOwnerAndUser(User owner, User authorizedUser) throws ServiceException;
    void removeContactForOwnerAndUser(User owner, User person) throws ServiceException;
}
