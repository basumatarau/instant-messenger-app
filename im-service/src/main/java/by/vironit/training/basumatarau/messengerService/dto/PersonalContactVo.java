package by.vironit.training.basumatarau.messengerService.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonalContactVo extends ContactEntryVo {

    private final UserProfileDto person;

    @JsonCreator
    public PersonalContactVo(
            @JsonProperty("id") Long id,
            @JsonProperty("owner") UserProfileDto owner,
            @JsonProperty("person") UserProfileDto person) {
        super(id, owner);
        this.person = person;
    }

    public UserProfileDto getPerson() {
        return person;
    }
}
