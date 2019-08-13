package by.vironit.training.basumatarau.messenger.dto;

import by.vironit.training.basumatarau.messenger.service.util.MessagingServiceVisitor;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscriptionVo
        extends ContactEntryVo {

    private final ChatRoomDto chatRoom;

    @JsonCreator
    public SubscriptionVo(
            @JsonProperty("id") Long id,
            @JsonProperty("owner") UserProfileDto owner,
            @JsonProperty("chat_room") ChatRoomDto chatRoom) {
        super(id, owner);
        this.chatRoom = chatRoom;
    }

    public ChatRoomDto getChatRoom() {
        return chatRoom;
    }

    @Override
    public void accept(
            MessagingServiceVisitor visitor,
            IncomingMessageDto msg)
            throws InstantiationException {
        visitor.visit(msg, this);
    }
}
