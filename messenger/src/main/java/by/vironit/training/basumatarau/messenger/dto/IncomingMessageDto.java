package by.vironit.training.basumatarau.messenger.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class IncomingMessageDto {

    @NotNull
    private Long contactEntryId;

    @NotNull
    @Size(max = 500)
    private String body;

    public IncomingMessageDto() {
    }

    public IncomingMessageDto(String body) {
        this.body = body;
    }

    public IncomingMessageDto(Long contactEntryId, String body) {
        this.contactEntryId = contactEntryId;
        this.body = body;
    }

    public Long getContactEntryId() {
        return contactEntryId;
    }

    public void setContactEntryId(Long contactEntryId) {
        this.contactEntryId = contactEntryId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
