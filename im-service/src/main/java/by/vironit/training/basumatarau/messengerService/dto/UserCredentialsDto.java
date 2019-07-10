package by.vironit.training.basumatarau.messengerService.dto;

public class UserCredentialsDto {
    private final String login;
    private final String password;

    public UserCredentialsDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
