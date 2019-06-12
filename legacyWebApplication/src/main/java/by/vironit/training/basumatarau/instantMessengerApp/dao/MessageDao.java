package by.vironit.training.basumatarau.instantMessengerApp.dao;

import by.vironit.training.basumatarau.instantMessengerApp.exception.DaoException;
import by.vironit.training.basumatarau.instantMessengerApp.model.Contact;
import by.vironit.training.basumatarau.instantMessengerApp.model.PrivateMessage;

import java.util.List;

public interface MessageDao extends CrudDao<PrivateMessage, Long>{
    List<PrivateMessage> getMessagesForContact(Contact contact) throws DaoException;

}
