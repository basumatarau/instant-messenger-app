package by.vironit.training.basumatarau.simpleMessengerApp.dao.impl;

import by.vironit.training.basumatarau.simpleMessengerApp.connection.ConnectionPool;

public abstract class BaseDao {
    private final ConnectionPool cPool;
    {
        cPool = ConnectionPool.getInstance();
    }

    protected ConnectionPool getConnectionPool() {
        return cPool;
    }

}
