package by.vironit.training.basumatarau.instantMessengerApp.persistenceTests;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbInit {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        init();
    }

    private static void init() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/im-db-training-project",
                "postgres",
                "password")) {
            new ScriptRunner(connection).runScript(new FileReader("schema-postgresql.sql"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
