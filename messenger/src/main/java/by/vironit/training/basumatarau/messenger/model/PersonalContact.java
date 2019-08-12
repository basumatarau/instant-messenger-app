package by.vironit.training.basumatarau.messenger.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@DiscriminatorValue(value = ContactEntry.PERSONAL_CONTACT_TYPE)
public class PersonalContact extends ContactEntry{

    @ManyToOne
    @JoinColumn(name = "id_person")
    private User person;

    @Column(name = "confirmed")
    private Boolean isConfirmed = Boolean.FALSE;

    public PersonalContact(){}

    private PersonalContact(ContactBuilder builder){
        super(builder);
        person = builder.person;
        isConfirmed = builder.isConfirmed;
    }

    public static class ContactBuilder
            extends ContactEntryBuilder<PersonalContact, ContactBuilder>{
        private User person;
        private Boolean isConfirmed;

        public ContactBuilder(){}

        public ContactBuilder person(User person){
            this.person = person;
            return this;
        }

        public ContactBuilder confirmed(Boolean isConfirmed) {
            this.isConfirmed = isConfirmed;
            return this;
        }

        @Override
        public PersonalContact build() throws InstantiationException {
            contactEntryBuildIntegrityCheck();
            return new PersonalContact(this);
        }

        @Override
        protected void contactEntryBuildIntegrityCheck() throws InstantiationException {
            super.contactEntryBuildIntegrityCheck();
            if (person == null || isConfirmed == null) {
                throw new InstantiationException(
                        "invalid or not sufficient data for " +
                                getClass().getName() +
                                " object instantiation: addressee has not been assigned");
            }
        }
    }

    //convenience method (to be executed within session)
    public void confirmContact() throws InstantiationException {
        setIsConfirmed(true);
        final PersonalContact newPersonalContact = new ContactBuilder()
                .person(getOwner())
                .owner(person)
                .confirmed(true)
                .build();
        person.getContactEntries().add(newPersonalContact);
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
        if (!super.equals(o)) return false;
        PersonalContact personalContact = (PersonalContact) o;
        return Objects.equals(person, personalContact.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), person);
    }
}
