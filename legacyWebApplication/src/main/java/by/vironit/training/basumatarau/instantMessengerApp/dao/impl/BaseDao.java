package by.vironit.training.basumatarau.instantMessengerApp.dao.impl;

import by.vironit.training.basumatarau.instantMessengerApp.connection.ConnectionPool;
import by.vironit.training.basumatarau.instantMessengerApp.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseDao {
    protected final ConnectionPool cPool;
    {
        cPool = ConnectionPool.getInstance();
    }

    long executeUpdate(String sqlStatement) throws DaoException {
        final Connection connection = cPool.takeConnection();
        try (connection;
             Statement statement = connection.createStatement()){

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

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DaoException("transaction rollback failure", ex);
            }
            throw new DaoException(e);
        }
        return -1;

    }
}
