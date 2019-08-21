package by.vironit.training.basumatarau.messenger.dto;

import javax.validation.constraints.NotNull;

public class NewChatRoomDto {
    @NotNull
    private final String chatRoomName;
    private final Long[] invitedUserIds;
    @NotNull
    private final Boolean pub;

    public NewChatRoomDto(String chatRoomName,
                          Long[] invitedUserIds,
                          Boolean isPub) {
        this.chatRoomName = chatRoomName;
        this.invitedUserIds = invitedUserIds;
        this.pub = isPub;
    }

    public Boolean getPub() {
        return pub;
    }

    public String getChatRoomName() {
        return chatRoomName;
    }

    public Long[] getInvitedUserIds() {
        return invitedUserIds;
    }
}
