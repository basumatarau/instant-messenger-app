package org.vironit.basumatarau.imWebApplication.model;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class ChatRoom {
    private Long id;
    private String name;
    private Date timeCreated;
    private Set<Subscriber> subscribers;
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
