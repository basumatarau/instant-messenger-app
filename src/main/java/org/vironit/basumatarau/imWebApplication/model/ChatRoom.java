package org.vironit.basumatarau.imWebApplication.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "chatrooms")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "timecreated",
            nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreated;

    @OneToMany(mappedBy = "chatRoom",
            orphanRemoval = true)
    private Set<Subscriber> subscribers;

    @Column(name = "public", nullable = false)
    private Boolean isPublic;

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

    public Set<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<Subscriber> subscribers) {
        this.subscribers = subscribers;
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
                Objects.equals(timeCreated, chatRoom.timeCreated) &&
                Objects.equals(subscribers, chatRoom.subscribers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, timeCreated, subscribers);
    }
}
