package by.vironit.training.basumatarau.messengerService.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAccountRegistrationDto {
    private final String firstName;
    private final String lastName;
    private final String nickName;
    private final String email;
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
