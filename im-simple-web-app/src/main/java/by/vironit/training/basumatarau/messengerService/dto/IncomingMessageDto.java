package by.vironit.training.basumatarau.messengerService.dto;

public class IncomingMessageDto {
    private String body;
    private Long contactId;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }
}
