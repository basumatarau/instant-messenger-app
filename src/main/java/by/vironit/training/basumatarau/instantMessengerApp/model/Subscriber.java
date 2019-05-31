package by.vironit.training.basumatarau.instantMessengerApp.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "subscribers")
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "subsriber_identity_generator")
    @SequenceGenerator(name = "subsriber_identity_generator",
            sequenceName = "subscribers_id_seq",
            //schema = "instant_messenger_db_schema",
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscriber that = (Subscriber) o;
        return Objects.equals(chatRoom, that.chatRoom) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatRoom, user);
    }
}
