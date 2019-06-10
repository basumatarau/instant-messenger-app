package by.vironit.training.basumatarau.instantMessengerApp.dto;

import by.vironit.training.basumatarau.instantMessengerApp.model.Contact;

public class ContactDto {
    private final Long id;
    private final Boolean confirmed;
    private final UserDto owner;
    private final UserDto person;

    public ContactDto(Long id,
                      Boolean confirmed,
                      UserDto owner,
                      UserDto person) {
        this.id = id;
        this.confirmed = confirmed;
        this.owner = owner;
        this.person = person;
    }

    public static ContactDto getDto(Contact contact){
        return new ContactDto(contact.getId(),
                contact.getIsConfirmed(),
                UserDto.getDto(contact.getOwner()),
                UserDto.getDto(contact.getPerson())
        );
    }

    public UserDto getOwner() {
        return owner;
    }

    public UserDto getPerson() {
        return person;
    }

    public Long getId() {
        return id;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }
}
