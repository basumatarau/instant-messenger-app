package org.vironit.basumatarau.imWebApplication.model;

import java.util.Objects;

public class Contact {
    private Long id;
    private User owner;
    private User person;
    private Boolean confirmed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getPerson() {
        return person;
    }

    public void setPerson(User person) {
        this.person = person;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(owner, contact.owner) &&
                Objects.equals(person, contact.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, person);
    }
}
