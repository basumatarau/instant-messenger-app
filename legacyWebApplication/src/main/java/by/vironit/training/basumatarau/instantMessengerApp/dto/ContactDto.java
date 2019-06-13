package by.vironit.training.basumatarau.instantMessengerApp.dto;

import by.vironit.training.basumatarau.instantMessengerApp.model.Contact;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactDto that = (ContactDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
