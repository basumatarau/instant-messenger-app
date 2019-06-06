package by.vironit.training.basumatarau.instantMessengerApp.dao;

import by.vironit.training.basumatarau.instantMessengerApp.dao.impl.UserDaoImpl;
import by.vironit.training.basumatarau.instantMessengerApp.model.Contact;
import by.vironit.training.basumatarau.instantMessengerApp.model.PrivateMessage;
import by.vironit.training.basumatarau.instantMessengerApp.model.Role;

public enum DaoProvider {
    DAO;

    public final CrudDao<Role, Integer> roleDao;
    public final UserDaoImpl userDao;
    public final CrudDao<Contact, Long> contactDao;
    public final CrudDao<PrivateMessage, Long> privateMessageDao;

    DaoProvider(){
        roleDao = null;
        userDao = new UserDaoImpl();
        contactDao = null;
        privateMessageDao = null;
    }
}
