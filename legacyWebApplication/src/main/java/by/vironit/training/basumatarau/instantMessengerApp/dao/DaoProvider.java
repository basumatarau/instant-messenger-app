package by.vironit.training.basumatarau.instantMessengerApp.dao;

import by.vironit.training.basumatarau.instantMessengerApp.connection.ConnectionPool;
import by.vironit.training.basumatarau.instantMessengerApp.dao.impl.UserDaoImpl;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ConnectionPoolException;
import by.vironit.training.basumatarau.instantMessengerApp.model.Contact;
import by.vironit.training.basumatarau.instantMessengerApp.model.PrivateMessage;
import by.vironit.training.basumatarau.instantMessengerApp.model.Role;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public enum DaoProvider {
    DAO;

    public final CrudDao<Role, Integer> roleDao;
    public final CrudDao<User, Long> userDao;
    public final CrudDao<Contact, Long> contactDao;
    public final CrudDao<PrivateMessage, Long> privateMessageDao;

    DaoProvider(){
        roleDao = null;
        userDao = new UserDaoImpl();
        contactDao = null;
        privateMessageDao = null;
    }

    long executeUpdate(String sqlStatement) throws SQLException, ConnectionPoolException {
        try(Connection connection = ConnectionPool.getInstance().takeConnection();
            Statement statement = connection.createStatement()
        ){

            if(sqlStatement.toLowerCase().matches("^\\s*insert.*")) {
                if (statement.executeUpdate(sqlStatement, Statement.RETURN_GENERATED_KEYS) == 1) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        return generatedKeys.getLong(1);
                    }
                }
            }else{
                return statement.executeUpdate(sqlStatement);
            }
        }
        return -1;
    }
}
