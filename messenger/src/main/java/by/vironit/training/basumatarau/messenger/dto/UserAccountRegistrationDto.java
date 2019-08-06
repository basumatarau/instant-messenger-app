package by.vironit.training.basumatarau.messenger.dto;

import by.vironit.training.basumatarau.messenger.validator.ValidEmail;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserAccountRegistrationDto {
    @Size(max = 150)
    private final String firstName;

    @Size(max = 150)
    private final String lastName;

    @NotNull
    @Size(min = 1, max = 60)
    private final String nickName;

    @ValidEmail
    @Size(min = 5, max = 160)
    private final String email;

    @NotNull
    @Size(min = 1, max = 500)
    private final String rawPassword;

    @JsonCreator
    public UserAccountRegistrationDto(
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("nickName") String nickName,
            @JsonProperty("email") String email,
            @JsonProperty("rawPassword") String rawPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.email = email;
        this.rawPassword = rawPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getEmail() {
        return email;
    }

    public String getRawPassword() {
        return rawPassword;
    }
}
