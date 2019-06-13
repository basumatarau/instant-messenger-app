package by.vironit.training.basumatarau.instantMessengerApp.dao;

import by.vironit.training.basumatarau.instantMessengerApp.dao.impl.ContactDaoImpl;
import by.vironit.training.basumatarau.instantMessengerApp.dao.impl.PrivateMessageDaoImpl;
import by.vironit.training.basumatarau.instantMessengerApp.dao.impl.RoleDaoImpl;
import by.vironit.training.basumatarau.instantMessengerApp.dao.impl.UserDaoImpl;

public enum DaoProvider {
    DAO;

    public final RoleDao roleDao;
    public final UserDao userDao;
    public final ContactDao contactDao;
    public final MessageDao messageDao;

    DaoProvider(){
        roleDao = RoleDaoImpl.getCashedProxy();
        userDao = new UserDaoImpl();
        contactDao = new ContactDaoImpl();
        messageDao = new PrivateMessageDaoImpl();
    }
}
