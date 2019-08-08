package by.vironit.training.basumatarau.messenger.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class MessageStatusInfoDto {
    private final boolean delivered;
    private final Date deliveredTime;
    private final boolean read;
    private final Date readTime;
    private final Long messageId;
    private final ContactEntryVo contactEntryVo;

    @JsonCreator
    public MessageStatusInfoDto(
            @JsonProperty("message_id") Long messageId,
            @JsonProperty("delivered") Boolean delivered,
            @JsonProperty("time_delivered") Date deliveredTime,
            @JsonProperty("read") Boolean read,
            @JsonProperty("time_read") Date readTime,
            @JsonProperty("contact_entry") ContactEntryVo contactEntryVo){
        this.delivered = delivered;
        this.deliveredTime = deliveredTime;
        this.read = read;
        this.readTime = readTime;
        this.messageId = messageId;
        this.contactEntryVo = contactEntryVo;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public Date getDeliveredTime() {
        return deliveredTime;
    }

    public boolean isRead() {
        return read;
    }

    public Date getReadTime() {
        return readTime;
    }

    public Long getMessageId() {
        return messageId;
    }

    public ContactEntryVo getContactEntryVo() {
        return contactEntryVo;
    }
}
