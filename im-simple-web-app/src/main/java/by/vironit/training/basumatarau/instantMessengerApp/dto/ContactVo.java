package by.vironit.training.basumatarau.instantMessengerApp.dto;

import by.vironit.training.basumatarau.instantMessengerApp.model.Contact;

import java.util.Objects;

public class ContactVo {
    private final Long id;
    private final Boolean confirmed;
    private final UserDto owner;
    private final UserDto person;

    public ContactVo(Long id,
                     Boolean confirmed,
                     UserDto owner,
                     UserDto person) {
        this.id = id;
        this.confirmed = confirmed;
        this.owner = owner;
        this.person = person;
    }

    public static ContactVo getDto(Contact contact){
        return new ContactVo(contact.getId(),
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
        ContactVo that = (ContactVo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
