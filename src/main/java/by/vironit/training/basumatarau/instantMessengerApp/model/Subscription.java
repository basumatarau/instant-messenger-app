package by.vironit.training.basumatarau.instantMessengerApp.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "subscriptions", schema = "instant_messenger_db_schema")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "subsription_identity_generator")
    @SequenceGenerator(name = "subsription_identity_generator",
            sequenceName = "subscriptions_id_seq",
            schema = "instant_messenger_db_schema",
            allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_chatroom",
            foreignKey = @ForeignKey,
            nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "id_user",
            foreignKey = @ForeignKey,
            nullable = false)
    private User user;

    @Column(name = "enteredchat",
            nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date enteredChat;

    @ManyToOne
    @JoinColumn(name = "id_userprivilege",
            foreignKey = @ForeignKey,
            nullable = false)
    private ChatRoomPrivilege privilege;

    @Column(name = "enabled", nullable = false)
    private Boolean isEnabled;

    public Subscription(){}

    private Subscription(SubscriptionBuilder builder){
        setUser(builder.user);
        this.chatRoom = builder.chatRoom;
        this.enteredChat = builder.enteredChat;
        this.privilege = builder.privilege;
        this.isEnabled = builder.isEnabled;
    }

    public static class SubscriptionBuilder {
        private ChatRoom chatRoom;
        private User user;
        private Date enteredChat;
        private ChatRoomPrivilege privilege;
        private Boolean isEnabled;

        public SubscriptionBuilder(){}

        public SubscriptionBuilder chatRoom(ChatRoom chatRoom){
            this.chatRoom = chatRoom;
            return this;
        }

        public SubscriptionBuilder user(User user){
            this.user = user;
            return this;
        }

        public SubscriptionBuilder enteredChat(Date enteredChat){
            this.enteredChat = enteredChat;
            return this;
        }

        public SubscriptionBuilder priviledge(ChatRoomPrivilege privilege){
            this.privilege = privilege;
            return this;
        }

        public SubscriptionBuilder enabled(Boolean enabled){
            this.isEnabled = enabled;
            return this;
        }

        public Subscription build() throws InstantiationException {
            buildDataIntegrityCheck();
            return new Subscription(this);
        }

        private void buildDataIntegrityCheck() throws InstantiationException{
            if(user == null
                    || chatRoom == null
                    || enteredChat == null
                    || privilege == null
                    || isEnabled == null){
                throw new InstantiationException(
                        "invalid or not sufficient data for Subscription object instantiation");
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        user.getSubscriptions().add(this);
        this.user = user;
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
        Subscription that = (Subscription) o;
        return Objects.equals(chatRoom, that.chatRoom) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatRoom, user);
    }
}
