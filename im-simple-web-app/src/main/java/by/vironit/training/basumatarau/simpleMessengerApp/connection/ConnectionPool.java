package by.vironit.training.basumatarau.simpleMessengerApp.connection;

import by.vironit.training.basumatarau.simpleMessengerApp.exception.ConnectionPoolException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static ConnectionPool instance;

    private BlockingQueue<Connection> connectionQueue;
    private BlockingQueue<Connection> givenAwayConnectionQueue;
    private Map<Connection, Connection> lookUpDictionary;

    private String driverName;
    private String url;
    private String login;
    private String password;
    private int poolSize;

    private ConnectionPool() {

        driverName = DatasourceProperties.DRIVER_NAME;
        url = DatasourceProperties.URL;
        login = DatasourceProperties.LOGIN;
        password = DatasourceProperties.PASSWORD;
        poolSize = DatasourceProperties.POOLSIZE;

        try {
            initPoolData();
        } catch (ConnectionPoolException e) {
            throw new RuntimeException("connection pool init failure", e);
        }
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    private void initPoolData() throws ConnectionPoolException {
        try {
            Class.forName(driverName);
            lookUpDictionary = new HashMap<>();
            connectionQueue = new ArrayBlockingQueue<>(poolSize);
            givenAwayConnectionQueue = new ArrayBlockingQueue<>(poolSize);

            for (int i = 0; i < poolSize; i++) {
                final Connection con = DriverManager.getConnection(url, login, password);
                final PooledConnectionInvocationHandler<Connection> invocationHandler
                        = new PooledConnectionInvocationHandler<>(con);

                Connection connectionProxy
                        = ((PooledConnection) Proxy.newProxyInstance(
                        con.getClass().getClassLoader(),
                        new Class[]{PooledConnection.class},
                        invocationHandler));
                connectionQueue.add(connectionProxy);
                lookUpDictionary.put(con, connectionProxy);
            }
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("driver not found", e);
        } catch (SQLException e) {
            throw new ConnectionPoolException("SQLException while connection pool init", e);
        } catch (InstantiationException e) {
            throw new ConnectionPoolException("failed to instantiate pooled connection", e);
        }
    }

    public void dispose() {
        clearConnectionQueue();
        lookUpDictionary.clear();
    }

    private void clearConnectionQueue() {
        try {
            closeConnectionQueue(givenAwayConnectionQueue);
            closeConnectionQueue(connectionQueue);
        } catch (SQLException e) {
            // logger.log
        }
    }

    public Connection takeConnection(){
        Connection connection;
        try {
            connection = connectionQueue.take();
            givenAwayConnectionQueue.add(connection);
        } catch (InterruptedException e) {
            throw new RuntimeException("connection pooling failure", e);
        }
        return connection;
    }

    public void closeConnection(ResultSet rs, PreparedStatement st, Connection con) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            //logger.log
        }
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            //logger.log
        }
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            //logger.log
        }
    }

    public void closeConnection(PreparedStatement st, Connection con) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            //logger.log
        }
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            //logger.log
        }
    }

    private void closeConnectionQueue(BlockingQueue<Connection> queue)
            throws SQLException {
        Connection connection;

        while ((connection = queue.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            ((PooledConnection) connection).reallyClose();
        }
    }

    /**
     * to be revised...
     * fixed ¯\_(ツ)_/¯
     */

    private interface PooledConnection extends Connection {
        void reallyClose();
    }

    private class PooledConnectionInvocationHandler<T extends Connection>
            implements InvocationHandler {
        private T invocationTarget;

        PooledConnectionInvocationHandler(T connection)
                throws InstantiationException {
            invocationTarget = connection;
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                throw new InstantiationException("failed to instantiate pooled connection");
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("close") && method.getParameterCount() == 0) {
                proxiedClose();
                return null;
            }else if (method.getName().equals("reallyClose") && method.getParameterCount() == 0) {
                final Method close
                        = invocationTarget.getClass().getDeclaredMethod("close");
                return close.invoke(invocationTarget);
            } else if(method.getName().equals("equals") && method.getParameterCount() == 1){
                return lookUpDictionary.get(invocationTarget) == args[0];
            }else {
                return method.invoke(invocationTarget, args);
            }
        }

        private void proxiedClose() throws SQLException {
            Connection conProxy = lookUpDictionary.get(invocationTarget);

            if (invocationTarget.isClosed()) {
                throw new SQLException(
                        "close() method invocation on closed connection instance");
            }
            if (invocationTarget.isReadOnly()) {
                invocationTarget.setReadOnly(false);
            }

            if (!givenAwayConnectionQueue.remove(conProxy)) {
                throw new SQLException("illegal state exception: \"in use\" connection " +
                        "queue doesn't have the connection instance");
            }
            if (!connectionQueue.offer(conProxy)) {
                throw new SQLException("illegal state exception: \"free\" connection " +
                        "queue already has the connection instance");
            }
        }
    }

}