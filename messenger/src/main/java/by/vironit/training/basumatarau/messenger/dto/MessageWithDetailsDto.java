package by.vironit.training.basumatarau.messenger.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class MessageWithDetailsDto extends MessageDto {

    private final MessageStatusInfoDto[] messageStatusInfo;

    @JsonCreator
    public MessageWithDetailsDto(
            @JsonProperty("id") Long id,
            @JsonProperty("author") UserProfileDto author,
            @JsonProperty("body") String body,
            @JsonProperty("timesent") Date timesent,
            @JsonProperty("contact_entry") ContactEntryVo contactEntryVo,
            @JsonProperty("message_status_info") MessageStatusInfoDto[] messageStatusInfo) {
        super(id, author, body, timesent, contactEntryVo);
        this.messageStatusInfo = messageStatusInfo;
    }

    public MessageStatusInfoDto[] getMessageStatusInfo() {
        return messageStatusInfo;
    }
}
