package by.vironit.training.basumatarau.instantMessengerApp.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@DiscriminatorValue(value = "dm")
public class DistributedMessage extends Message {
    @ManyToOne
    @JoinColumn(
            name = "id_chatroom",
            foreignKey = @ForeignKey)
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
