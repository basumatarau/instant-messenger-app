package by.vironit.training.basumatarau.messengerService.dao.impl;

import by.vironit.training.basumatarau.messengerService.connection.ConnectionPool;

public abstract class BaseDao {
    private final ConnectionPool cPool;
    {
        cPool = ConnectionPool.getInstance();
    }

    protected ConnectionPool getConnectionPool() {
        return cPool;
    }

}
