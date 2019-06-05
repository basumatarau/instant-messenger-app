package by.vironit.training.basumatarau.instantMessengerApp.connection;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConnectionProperties {
    private static ConnectionProperties instance;
    private Properties props;

    private ConnectionProperties(){
        props = new Properties();
        try(final FileReader reader = new FileReader("connection.properties")){
            props.load(reader);
        } catch (IOException e) {
            throw new RuntimeException("connection pool init failure", e);
        }
    }

    public static ConnectionProperties getInstance() {
        if(instance == null){
            synchronized (ConnectionProperties.class){
                if(instance == null){
                    instance = new ConnectionProperties();
                }
            }
        }
        return instance;
    }

    public String getValue(String key) {
        return getInstance().props.getProperty(key);
    }
}
