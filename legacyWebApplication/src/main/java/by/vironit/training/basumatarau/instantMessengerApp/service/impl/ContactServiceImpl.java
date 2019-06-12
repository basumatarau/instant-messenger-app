package by.vironit.training.basumatarau.instantMessengerApp.service.impl;

import by.vironit.training.basumatarau.instantMessengerApp.dao.ContactDao;
import by.vironit.training.basumatarau.instantMessengerApp.dao.DaoProvider;
import by.vironit.training.basumatarau.instantMessengerApp.dto.ContactDto;
import by.vironit.training.basumatarau.instantMessengerApp.exception.DaoException;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ServiceException;
import by.vironit.training.basumatarau.instantMessengerApp.model.Contact;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;
import by.vironit.training.basumatarau.instantMessengerApp.service.ContactService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ContactServiceImpl implements ContactService {
    private final ContactDao contactDao = DaoProvider.DAO.contactDao;


    @Override
    public List<ContactDto> findAllContactsForUser(User user) throws ServiceException{
        try {
            return contactDao.getContactsForUser(user)
                    .stream()
                    .map((ContactDto::getDto))
                    .collect(Collectors.toList());
        } catch (DaoException e) {
            throw new ServiceException("failed to retrieve user contacts", e);
        }
    }

    @Override
    public void sendContactRequestToUser(User user, User person)
            throws ServiceException {
        try {

            final Contact newContactRequest
                    = new Contact.ContactBuilder()
                    .owner(user)
                    .person(person)
                    .confirmed(false)
                    .build();

            contactDao.save(newContactRequest);
        } catch (DaoException | InstantiationException e) {
            throw new ServiceException("failed to add contact", e);
        }
    }

    @Override
    public void removeContact(Contact contact) throws ServiceException {
        try {
            contactDao.delete(contact);
        } catch (DaoException e) {
            throw new ServiceException("failed to remove contact", e);
        }
    }

    @Override
    public void removeContactForOwnerAndUser(User owner, User person) throws ServiceException {
        try {
            final Optional<Contact> contactOne = contactDao.getContactsFoOwnerAndPerson(owner, person);
            if(contactOne.isPresent()){
                contactDao.delete(contactOne.get());
            }

            final Optional<Contact> contactTwo = contactDao.getContactsFoOwnerAndPerson(person, owner);
            if(contactTwo.isPresent()){
                contactDao.delete(contactTwo.get());
            }
        } catch (DaoException e) {
            throw new ServiceException("failed to fetch contact", e);
        }
    }

    @Override
    public void confirmContactRequest(Contact contact) throws ServiceException {
        try {
            final Contact newContactRequest
                    = new Contact.ContactBuilder()
                    .owner(contact.getPerson())
                    .person(contact.getOwner())
                    .confirmed(true)
                    .build();
            contactDao.save(newContactRequest);
            contact.setIsConfirmed(true);
            contactDao.update(contact);
        } catch (DaoException | InstantiationException e) {
            throw new ServiceException("failed to add contact", e);
        }
    }

    @Override
    public void declineContactRequest(Contact contact) throws ServiceException {
        try {
            contactDao.delete(contact);
        } catch (DaoException e) {
            throw new ServiceException("failed to add contact", e);
        }
    }

    @Override
    public Optional<Contact> findContactById(Long id) throws ServiceException {
        try {
            return contactDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("failed to fetch contact with id=" + id, e);
        }
    }

    @Override
    public Optional<Contact> getContactByOwnerAndUser(User owner, User authorizedUser)
            throws ServiceException {
        try {
            return contactDao.getContactsFoOwnerAndPerson(owner, authorizedUser);
        } catch (DaoException e) {
            throw new ServiceException("failed to fetch contact", e);
        }
    }
}
