package by.vironit.training.basumatarau.messenger.model;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@DiscriminatorValue(value = Message.DISTRIBUTED_MESSAGE_TYPE)
public class DistributedMessage extends Message {

    @ManyToOne
    @JoinColumn(
            name = "id_chatroom",
            foreignKey = @ForeignKey)
    private ChatRoom chatRoom;

    @OneToMany(mappedBy = "message",
            orphanRemoval = true,
            cascade = {CascadeType.ALL})
    private Set<StatusInfo> deliveries = new HashSet<>();

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public Set<StatusInfo> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(Set<StatusInfo> deliveries) {
        this.deliveries = deliveries;
    }

    public DistributedMessage(){}

    protected DistributedMessage(DistributedMessageBuilder builder){
        super(builder);
        this.deliveries = builder.deliveries;
        this.chatRoom = builder.chatRoom;
    }

    @PrePersist
    private void addMessageInfo(){
        this.setDeliveries(
                this.chatRoom.getSubscriptions()
                        .stream()
                        .map(subscription -> new StatusInfo.StatusInfoBuilder()
                                .delivered(false)
                                .read(false)
                                .message(this)
                                .contactEntry(subscription)
                                .build())
                        .collect(Collectors.toSet())
        );

        this.getResources().forEach(
                messageResource -> messageResource.setMessage(this)
        );
    }

    public static class DistributedMessageBuilder
            extends MessageBuilder<DistributedMessage, DistributedMessageBuilder>{
        public DistributedMessageBuilder() {
        }

        private Set<StatusInfo> deliveries;
        private ChatRoom chatRoom;

        public DistributedMessageBuilder chatRoom(ChatRoom chatRoom){
            this.chatRoom = chatRoom;
            return this;
        }

        public DistributedMessageBuilder deliveries(Set<StatusInfo> deliveries){
            this.deliveries = deliveries;
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
