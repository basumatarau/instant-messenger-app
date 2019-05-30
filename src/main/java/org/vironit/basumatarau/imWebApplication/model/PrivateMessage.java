package org.vironit.basumatarau.imWebApplication.model;

import java.util.Objects;

public class PrivateMessage extends Message {
    private Contact contact;

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PrivateMessage that = (PrivateMessage) o;
        return Objects.equals(contact, that.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), contact);
    }
}
