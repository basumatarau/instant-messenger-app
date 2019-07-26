package by.vironit.training.basumatarau.messenger.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonalContactVo extends ContactEntryVo {

    private final UserProfileDto person;
    private final Boolean confirmed;

    @JsonCreator
    public PersonalContactVo(
            @JsonProperty("id") Long id,
            @JsonProperty("owner") UserProfileDto owner,
            @JsonProperty("person") UserProfileDto person,
            @JsonProperty("confirmed") Boolean confirmed) {
        super(id, owner);
        this.person = person;
        this.confirmed = confirmed;
    }

    public UserProfileDto getPerson() {
        return person;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }
}
