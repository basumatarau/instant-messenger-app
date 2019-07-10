package by.vironit.training.basumatarau.instantMessengerApp.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@DiscriminatorValue(value = ContactEntry.SUBSCRIPTION_TYPE)
public class Subscription extends ContactEntry{

    @ManyToOne
    @JoinColumn(name = "id_chatroom",
            foreignKey = @ForeignKey)
    private ChatRoom chatRoom;

    @Column(name = "enteredchat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enteredChat;

    @ManyToOne
    @JoinColumn(name = "id_userprivilege",
            foreignKey = @ForeignKey)
    private ChatRoomPrivilege privilege;

    @Column(name = "enabled")
    private Boolean isEnabled = Boolean.FALSE;

    public Subscription(){}

    private Subscription(SubscriptionBuilder builder){
        super(builder);
        this.chatRoom = builder.chatRoom;
        this.enteredChat = builder.enteredChat;
        this.privilege = builder.privilege;
        this.isEnabled = builder.isEnabled;
    }

    public static class SubscriptionBuilder
            extends ContactEntryBuilder<Subscription, SubscriptionBuilder>{
        private ChatRoom chatRoom;
        private Date enteredChat;
        private ChatRoomPrivilege privilege;
        private Boolean isEnabled;

        public SubscriptionBuilder(){}

        public SubscriptionBuilder chatRoom(ChatRoom chatRoom){
            this.chatRoom = chatRoom;
            return this;
        }

        public SubscriptionBuilder enteredChat(Date enteredChat){
            this.enteredChat = enteredChat;
            return this;
        }

        public SubscriptionBuilder privilege(ChatRoomPrivilege privilege){
            this.privilege = privilege;
            return this;
        }

        public SubscriptionBuilder enabled(Boolean enabled){
            this.isEnabled = enabled;
            return this;
        }

        @Override
        public Subscription build() throws InstantiationException {
            contactEntryBuildIntegrityCheck();
            return new Subscription(this);
        }

        @Override
        protected void contactEntryBuildIntegrityCheck() throws InstantiationException {
            super.contactEntryBuildIntegrityCheck();
            if (chatRoom == null || enteredChat == null || privilege == null || isEnabled == null) {
                throw new InstantiationException(
                        "invalid or not sufficient data for " +
                                getClass().getName() +
                                " object instantiation: addressee has not been assigned");
            }
        }
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public Date getEnteredChat() {
        return enteredChat;
    }

    public void setEnteredChat(Date enteredChat) {
        this.enteredChat = enteredChat;
    }

    public ChatRoomPrivilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(ChatRoomPrivilege privilege) {
        this.privilege = privilege;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subscription that = (Subscription) o;
        return Objects.equals(chatRoom, that.chatRoom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatRoom, super.hashCode());
    }
}
