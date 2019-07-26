package by.vironit.training.basumatarau.messenger.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscriptionVo extends ContactEntryVo {

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
}
