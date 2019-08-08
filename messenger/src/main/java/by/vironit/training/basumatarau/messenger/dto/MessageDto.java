package by.vironit.training.basumatarau.messenger.dto;

import by.vironit.training.basumatarau.messenger.model.StatusInfo;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Date;

public class MessageDto {
    private final Long id;
    private final UserProfileDto author;
    private final String body;
    private final Date timesent;
    private final ContactEntryVo contactEntryVo;
    private final MessageStatusInfoDto[] messageStatusInfo;

    @JsonCreator
    public MessageDto(
            @JsonProperty("id") Long id,
            @JsonProperty("author") UserProfileDto author,
            @JsonProperty("body") String body,
            @JsonProperty("timesent") Date timesent,
            @JsonProperty("contact_entry") ContactEntryVo contactEntryVo,
            @JsonProperty("message_status_info") MessageStatusInfoDto[] messageStatusInfo) {
        this.id = id;
        this.author = author;
        this.body = body;
        this.timesent = timesent;
        this.contactEntryVo = contactEntryVo;
        this.messageStatusInfo = messageStatusInfo;
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

    public MessageStatusInfoDto[] getMessageStatusInfo() {
        return messageStatusInfo;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "id=" + id +
                ", author=" + author +
                ", body='" + body + '\'' +
                ", timesent=" + timesent +
                ", contactEntryVo=" + contactEntryVo +
                ", messageStatusInfo=" + Arrays.toString(messageStatusInfo) +
                '}';
    }
}
