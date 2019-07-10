package by.vironit.training.basumatarau.instantMessengerApp.persistenceTests;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.runners.model.InitializationError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbInit {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        init();
    }

    private static void init() throws ClassNotFoundException, SQLException, IOException {
        Class.forName("org.postgresql.Driver");

        Properties dbInitProperties = new Properties();

        try(final InputStream propStream = ClassLoader.getSystemResourceAsStream("test.properties")){
            if(propStream == null)
                throw new InitializationError("property file has not been found");
            dbInitProperties.load(propStream);
        } catch (InitializationError | IOException e) {
            throw new RuntimeException("db init failure", e);
        }

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/im-legacy-db-training-project",
                dbInitProperties.getProperty("spring.datasource.username"),
                dbInitProperties.getProperty("spring.datasource.password"));
             final InputStream dbInitScript
                     = ClassLoader.getSystemResourceAsStream("db-init-script.psql")
        ) {
            if(dbInitScript == null){
                throw new RuntimeException("db init failure");
            }
            new ScriptRunner(connection).runScript(new InputStreamReader(dbInitScript));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
