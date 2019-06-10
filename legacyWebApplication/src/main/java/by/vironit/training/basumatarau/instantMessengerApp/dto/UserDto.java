package by.vironit.training.basumatarau.instantMessengerApp.dto;

import by.vironit.training.basumatarau.instantMessengerApp.model.User;

public class UserDto {
    private final Long id;
    private final String fName;
    private final String lName;
    private final String nName;
    private final String email;
    private final Boolean enabled;

    public UserDto(Long id,
                   String fName,
                   String lName,
                   String nName,
                   String email,
                   Boolean enabled) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.nName = nName;
        this.email = email;
        this.enabled = enabled;
    }

    public static UserDto getDto(User user){
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getNickName(),
                user.getEmail(),
                user.getEnabled());
    }

    public Boolean getEnabled() {
        return enabled;
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
