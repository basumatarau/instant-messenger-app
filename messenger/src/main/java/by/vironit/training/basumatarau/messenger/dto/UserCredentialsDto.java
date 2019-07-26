package by.vironit.training.basumatarau.messenger.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserCredentialsDto {
    private final String login;
    private final String password;

    @JsonCreator
    public UserCredentialsDto(
            @JsonProperty("login") String login,
            @JsonProperty("password") String password) {
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
