package by.vironit.training.basumatarau.messengerService.dto;

public class UserAccountRegistrationDto {
    private final String firstName;
    private final String lastName;
    private final String nickName;
    private final String email;
    private final String rawPassword;

    public UserAccountRegistrationDto(String firstName,
                                      String lastName,
                                      String nickName,
                                      String email,
                                      String rawPassword) {
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
