package by.vironit.training.basumatarau.messenger.dto;

public class PrivateMessageDto {

    private String body;

    public PrivateMessageDto() {
    }

    public PrivateMessageDto(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
