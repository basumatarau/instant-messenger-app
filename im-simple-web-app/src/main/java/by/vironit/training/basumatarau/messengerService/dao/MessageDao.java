package by.vironit.training.basumatarau.messengerService.dao;

import by.vironit.training.basumatarau.messengerService.exception.DaoException;
import by.vironit.training.basumatarau.messengerService.model.Contact;
import by.vironit.training.basumatarau.messengerService.model.PrivateMessage;

import java.util.List;

public interface MessageDao extends CrudDao<PrivateMessage, Long>{
    List<PrivateMessage> getMessagesForContact(Contact contact) throws DaoException;

}
