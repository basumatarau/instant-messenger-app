package by.vironit.training.basumatarau.messenger.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@DiscriminatorValue(value = Message.DISTRIBUTED_MESSAGE_TYPE)
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

    public DistributedMessage(){}

    protected DistributedMessage(DistributedMessageBuilder builder){
        super(builder);
        this.chatRoom = builder.chatRoom;
    }

    public static class DistributedMessageBuilder
            extends MessageBuilder<DistributedMessage, DistributedMessageBuilder>{
        public DistributedMessageBuilder() {
        }

        private ChatRoom chatRoom;

        public DistributedMessageBuilder chatRoom(ChatRoom chatRoom){
            this.chatRoom = chatRoom;
            return this;
        }

        @Override
        protected void messageBuildIntegrityCheck() throws InstantiationException {
            super.messageBuildIntegrityCheck();
            if(chatRoom == null){
                throw new InstantiationException(
                        "invalid or not sufficient data for " +
                                getClass().getName() +
                                " object instantiation: chatRoom has not been assigned");
            }
        }

        @Override
        public DistributedMessage build() throws InstantiationException {
            messageBuildIntegrityCheck();
            return new DistributedMessage(this);
        }
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
