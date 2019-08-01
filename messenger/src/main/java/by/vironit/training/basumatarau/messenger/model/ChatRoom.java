package by.vironit.training.basumatarau.messenger.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "chatrooms", schema = "instant_messenger_db_schema")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "chatroom_identity_generator")
    @SequenceGenerator(name = "chatroom_identity_generator",
            sequenceName = "chatrooms_id_seq",
            schema = "instant_messenger_db_schema",
            allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "timecreated",
            nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreated;

    @OneToMany(mappedBy = "chatRoom",
            orphanRemoval = true)
    private Set<Subscription> subscriptions;

    @Column(name = "public", nullable = false)
    private Boolean isPublic;

    public ChatRoom(){}

    private ChatRoom(ChatRoomBuilder builder){
        this.name = builder.name;
        this.timeCreated = builder.timeCreated;
        this.subscriptions = builder.subscriptions;
        this.isPublic = builder.isPublic;
    }

    public static class ChatRoomBuilder{
        private String name;
        private Date timeCreated;
        private Set<Subscription> subscriptions;
        private Boolean isPublic;

        public ChatRoomBuilder(){}

        public ChatRoomBuilder name(String name){
            this.name = name;
            return this;
        }

        public ChatRoomBuilder timeCreated(Date timeCreated){
            this.timeCreated = timeCreated;
            return this;
        }

        public ChatRoomBuilder subscribers(Set<Subscription> subscribers){
            this.subscriptions = subscribers;
            return this;
        }

        public ChatRoomBuilder isPublic(Boolean isPublic){
            this.isPublic = isPublic;
            return this;
        }

        public ChatRoom build() throws InstantiationException {
            buildDataIntegrityCheck();
            return new ChatRoom(this);
        }

        private void buildDataIntegrityCheck() throws InstantiationException {
            if(name == null
                    || timeCreated == null
                    || isPublic == null){
                throw new InstantiationException(
                        "invalid or not sufficient data for ChatRoom object instantiation");
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Set<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatRoom chatRoom = (ChatRoom) o;
        return Objects.equals(name, chatRoom.name) &&
                Objects.equals(timeCreated, chatRoom.timeCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, timeCreated);
    }
}
