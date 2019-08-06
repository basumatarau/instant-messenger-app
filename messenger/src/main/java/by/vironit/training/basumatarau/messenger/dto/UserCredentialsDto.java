package by.vironit.training.basumatarau.messenger.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class UserCredentialsDto {

    @NotNull
    @Max(value = 160)
    private final String login;

    @NotNull
    @Max(value = 500)
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
