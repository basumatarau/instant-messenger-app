package by.vironit.training.basumatarau.instantMessengerApp.dto;

public class UserDto {
    public enum Status {
        MY_FRIEND, PENDING, STRANGER
    }

    private String id;
    private String firstName;
    private String lastName;
    private String nickName;
    private String email;
    private Status status;

}
