package by.vironit.training.basumatarau.messengerService.dto;

import java.util.Objects;

public class ContactEntryVo {
    private final Long id;
    private final UserProfileDto owner;

    public ContactEntryVo(Long id, UserProfileDto owner) {
        this.id = id;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public UserProfileDto getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactEntryVo that = (ContactEntryVo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
