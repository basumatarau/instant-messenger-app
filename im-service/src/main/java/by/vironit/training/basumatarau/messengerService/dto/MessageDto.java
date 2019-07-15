package by.vironit.training.basumatarau.messengerService.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class MessageDto {
    private final Long id;
    private final UserProfileDto author;
    private final String body;
    private final Date timesent;
    private final ContactEntryVo contactEntryVo;

    @JsonCreator
    public MessageDto(
            @JsonProperty("id") Long id,
            @JsonProperty("author") UserProfileDto author,
            @JsonProperty("body") String body,
            @JsonProperty("timesent") Date timesent,
            @JsonProperty("contact_entry") ContactEntryVo contactEntryVo) {
        this.id = id;
        this.author = author;
        this.body = body;
        this.timesent = timesent;
        this.contactEntryVo = contactEntryVo;
    }

    public Long getId() {
        return id;
    }

    public UserProfileDto getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public Date getTimesent() {
        return timesent;
    }

    public ContactEntryVo getContactEntryVo() {
        return contactEntryVo;
    }
}
