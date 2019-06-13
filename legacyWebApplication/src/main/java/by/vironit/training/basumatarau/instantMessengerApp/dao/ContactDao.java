package by.vironit.training.basumatarau.instantMessengerApp.dao;

import by.vironit.training.basumatarau.instantMessengerApp.exception.DaoException;
import by.vironit.training.basumatarau.instantMessengerApp.model.Contact;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;

import java.util.List;
import java.util.Optional;

public interface ContactDao extends CrudDao<Contact, Long>{
    List<Contact> getContactsForUser(User user) throws DaoException;
    List<Contact> getContactsWithConversationsForUser(User user) throws DaoException;
    Optional<Contact> getContactsFoOwnerAndPerson(User owner, User person) throws DaoException;
}
