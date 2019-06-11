package by.vironit.training.basumatarau.instantMessengerApp.connection;

import by.vironit.training.basumatarau.instantMessengerApp.exception.ConnectionPoolException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static ConnectionPool instance;

    private static final String DB_DRIVER = "datasource.driver";
    private static final String DB_URL = "datasource.url";
    private static final String DB_LOGIN = "datasource.username";
    private static final String DB_PASSWORD = "datasource.password";
    private static final String DB_POOL_SIZE = "datasource.poolsize";

    private BlockingQueue<Connection> connectionQueue;
    private BlockingQueue<Connection> givenAwayConnectionQueue;
    private String driverName;
    private String url;
    private String login;
    private String password;
    private int poolSize;

    private ConnectionPool() {
        //todo fix properties loading ... omfg
        //final ConnectionProperties propManager = ConnectionProperties.getInstance();
/*

        driverName = propManager.getValue(DB_DRIVER);
        url = propManager.getValue(DB_URL);
        login = propManager.getValue(DB_LOGIN);
        password = propManager.getValue(DB_PASSWORD);
        poolSize = Integer.parseInt(propManager.getValue(DB_POOL_SIZE));
*/

        driverName = "org.postgresql.Driver";
        url = "jdbc:postgresql://localhost:5432/im-legacy-db-training-project";
        login = "postgres";
        password = "password";
        poolSize = 20;


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
            connectionQueue = new ArrayBlockingQueue<>(poolSize);
            givenAwayConnectionQueue = new ArrayBlockingQueue<>(poolSize);

            for (int i = 0; i < poolSize; i++) {
                final Connection con = DriverManager.getConnection(url, login, password);
                final PooledConnectionInvocationHandler<Connection> invocationHandler
                        = new PooledConnectionInvocationHandler<>(con);

                Connection connection
                        = ((PooledConnection) Proxy.newProxyInstance(
                        con.getClass().getClassLoader(),
                        new Class[]{PooledConnection.class},
                        invocationHandler));

                connectionQueue.add(connection);
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
     * TODO TODO TODO fix the modafaka
     * doesn't work :((((((((((((((
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
            }
            if (method.getName().equals("reallyClose") && method.getParameterCount() == 0) {
                final Method close
                        = invocationTarget.getClass().getDeclaredMethod("close");
                return close.invoke(invocationTarget);
            }
            return method.invoke(invocationTarget, args);
        }

        private void proxiedClose() throws SQLException {
            if (invocationTarget.isClosed()) {
                throw new SQLException(
                        "close() method invocation on closed connection instance");
            }
            if (invocationTarget.isReadOnly()) {
                invocationTarget.setReadOnly(false);
            }
            if (!givenAwayConnectionQueue.remove(invocationTarget)) {
                throw new SQLException("illegal state exception: \"in use\" connection " +
                        "queue doesn't have the connection instance");
            }
            if (!connectionQueue.offer(invocationTarget)) {
                throw new SQLException("illegal state exception: \"free\" connection " +
                        "queue already has the connection instance");
            }
        }
    }

}