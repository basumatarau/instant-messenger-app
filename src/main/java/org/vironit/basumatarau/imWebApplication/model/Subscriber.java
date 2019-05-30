package org.vironit.basumatarau.imWebApplication.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "subscriber")
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_chatroom",
            foreignKey = @ForeignKey,
            nullable = false)
    private ChatRoom chatRoom;

    @OneToMany
    @JoinColumn(name = "id_user",
            foreignKey = @ForeignKey,
            nullable = false)
    private User user;

    @Column(name = "enteredchat",
            nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date enteredChat;

    @OneToMany
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
