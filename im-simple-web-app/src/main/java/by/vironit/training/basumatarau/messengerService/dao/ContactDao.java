package by.vironit.training.basumatarau.messengerService.dao;

import by.vironit.training.basumatarau.messengerService.exception.DaoException;
import by.vironit.training.basumatarau.messengerService.model.Contact;
import by.vironit.training.basumatarau.messengerService.model.PrivateMessage;
import by.vironit.training.basumatarau.messengerService.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ContactDao extends CrudDao<Contact, Long>{
    List<Contact> getContactsForUser(User user) throws DaoException;
    Map<Contact, PrivateMessage> getContactsWithLastMessageForUser(User user) throws DaoException;
    Optional<Contact> getContactsFoOwnerAndPerson(User owner, User person) throws DaoException;
}
