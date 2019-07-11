package by.vironit.training.basumatarau.simpleMessengerApp.dto;

import by.vironit.training.basumatarau.simpleMessengerApp.model.User;

public class UserDto {
    public enum Status{
        MY_FRIEND, PENDING, STRANGER
    }
    private final Long id;
    private final String fName;
    private final String lName;
    private final String nName;
    private final String email;
    private Status status;

    public UserDto(Long id,
                   String fName,
                   String lName,
                   String nName,
                   String email,
                   Status status) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.nName = nName;
        this.email = email;
        this.status = status;
    }

    public static UserDto getDto(User user){
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getNickName(),
                user.getEmail(),
                Status.STRANGER);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getnName() {
        return nName;
    }

    public String getEmail() {
        return email;
    }
}
