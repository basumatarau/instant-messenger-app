package by.vironit.training.basumatarau.messenger.dto;

import by.vironit.training.basumatarau.messenger.util.MessagingServiceVisitor;
import by.vironit.training.basumatarau.messenger.util.MessagingServiceVisitorImpl;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscriptionVo
        extends ContactEntryVo {

    private final ChatRoomDto chatRoom;

    @JsonCreator
    public SubscriptionVo(
            @JsonProperty("id") Long id,
            @JsonProperty("owner") UserProfileDto owner,
            @JsonProperty("chatRoom") ChatRoomDto chatRoom) {
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
