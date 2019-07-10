package by.vironit.training.basumatarau.instantMessengerApp.dto;

import by.vironit.training.basumatarau.instantMessengerApp.model.PrivateMessage;

import java.util.Date;

public class MessageDto {
    private final Long id;
    private final UserDto author;
    private final String body;
    private final Date timesent;
    private final ContactVo contact;

    public MessageDto(Long id,
                      UserDto author,
                      String body,
                      Date timesent,
                      ContactVo contact) {
        this.id = id;
        this.author = author;
        this.body = body;
        this.timesent = timesent;
        this.contact = contact;
    }

    public static MessageDto getDto(PrivateMessage message){
        return new MessageDto(
            message.getId(),
            UserDto.getDto(message.getAuthor()),
            message.getBody(),
            message.getTimeSent(),
            ContactVo.getDto(message.getContact())
        );
    }

    public Long getId() {
        return id;
    }

    public UserDto getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public Date getTimesent() {
        return timesent;
    }

    public ContactVo getContact() {
        return contact;
    }
}
