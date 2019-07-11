package by.vironit.training.basumatarau.simpleMessengerApp.dao;

import by.vironit.training.basumatarau.simpleMessengerApp.exception.DaoException;
import by.vironit.training.basumatarau.simpleMessengerApp.model.Contact;
import by.vironit.training.basumatarau.simpleMessengerApp.model.PrivateMessage;

import java.util.List;

public interface MessageDao extends CrudDao<PrivateMessage, Long>{
    List<PrivateMessage> getMessagesForContact(Contact contact) throws DaoException;

}
