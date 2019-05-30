package org.vironit.basumatarau.imWebApplication.model;

import java.util.Date;
import java.util.Objects;

public abstract class Message {
    private Long id;
    private String body;
    private User author;
    private Date timeSent;
    private MsgResource resource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Date timeSent) {
        this.timeSent = timeSent;
    }

    public MsgResource getResource() {
        return resource;
    }

    public void setResource(MsgResource resource) {
        this.resource = resource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(body, message.body) &&
                Objects.equals(author, message.author) &&
                Objects.equals(timeSent, message.timeSent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body, author, timeSent);
    }
}
