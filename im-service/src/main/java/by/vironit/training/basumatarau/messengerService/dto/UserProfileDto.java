package by.vironit.training.basumatarau.messengerService.dto;

public class UserProfileDto {

    public enum Status{
        MY_FRIEND, PENDING, STRANGER
    }

    private String id;
    private String firstName;
    private String lastName;
    private String nickName;
    private String email;
    private Status status;

    public UserProfileDto(){}

    public UserProfileDto(String id,
                          String firstName,
                          String lastName,
                          String nickName,
                          String email,
                          Status status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.email = email;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
