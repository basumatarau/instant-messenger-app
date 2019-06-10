package by.vironit.training.basumatarau.instantMessengerApp.dao.impl;

import by.vironit.training.basumatarau.instantMessengerApp.connection.ConnectionPool;

public abstract class BaseDao {
    private final ConnectionPool cPool;
    {
        cPool = ConnectionPool.getInstance();
    }

    protected ConnectionPool getConnectionPool() {
        return cPool;
    }

}
