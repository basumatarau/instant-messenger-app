package org.vironit.basumatarau.imWebApplication.model;

import java.util.Objects;

public class DistributedMessage extends Message {
    private ChatRoom chatRoom;

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DistributedMessage that = (DistributedMessage) o;
        return Objects.equals(chatRoom, that.chatRoom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), chatRoom);
    }
}
