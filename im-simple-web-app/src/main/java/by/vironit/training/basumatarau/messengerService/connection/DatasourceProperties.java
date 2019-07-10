package by.vironit.training.basumatarau.messengerService.connection;

public interface DatasourceProperties {
    String DRIVER_NAME = "org.postgresql.Driver";
    String URL = "jdbc:postgresql://localhost:5432/im-legacy-db-training-project";
    String LOGIN = "postgres";
    String PASSWORD = "password";
    Integer POOLSIZE = 20;
}
