package by.vironit.training.basumatarau;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbinit {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        init();
    }

    private static void init() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/im-db-training-project",
                "postgres",
                "password")) {
            new ScriptRunner(connection).runScript(new FileReader("vironit-trainig-db-script.psql"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
