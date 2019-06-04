package by.vironit.training.basumatarau.instantMessengerApp.connection;

import java.sql.Connection;

public abstract class ConnectionAdapter implements Connection {

    protected Connection connection;

    public ConnectionAdapter(Connection connection){
        this.connection = connection;
    }


}
