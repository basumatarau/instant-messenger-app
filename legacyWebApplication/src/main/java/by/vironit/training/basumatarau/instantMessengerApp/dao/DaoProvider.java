package by.vironit.training.basumatarau.instantMessengerApp.dao;

import by.vironit.training.basumatarau.instantMessengerApp.dao.impl.ContactDaoImpl;
import by.vironit.training.basumatarau.instantMessengerApp.dao.impl.PrivateMessageDaoImpl;
import by.vironit.training.basumatarau.instantMessengerApp.dao.impl.UserDaoImpl;
import by.vironit.training.basumatarau.instantMessengerApp.model.Role;

public enum DaoProvider {
    DAO;

    public final CrudDao<Role, Integer> roleDao;
    public final UserDao userDao;
    public final ContactDao contactDao;
    public final MessageDao messageDao;

    DaoProvider(){
        roleDao = null;
        userDao = new UserDaoImpl();
        contactDao = new ContactDaoImpl();
        messageDao = new PrivateMessageDaoImpl();
    }
}
