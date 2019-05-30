package org.vironit.basumatarau.imWebApplication.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_owner")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "id_person")
    private User person;

    @Column(name = "confirmed", nullable = false)
    private Boolean isConfirmed;

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

    public Boolean getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
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
