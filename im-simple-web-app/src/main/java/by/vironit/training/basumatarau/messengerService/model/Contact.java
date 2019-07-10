package by.vironit.training.basumatarau.messengerService.model;

import java.util.Objects;

public class Contact {
    private Long id;
    private User owner;
    private User person;
    private Boolean isConfirmed;

    public Contact(){}

    private Contact(ContactBuilder builder){
        owner = builder.owner;
        person = builder.person;
        isConfirmed = builder.isConfirmed;
        id = builder.id;
    }

    public static class ContactBuilder{
        private Long id;
        private User owner;
        private User person;
        private Boolean isConfirmed;

        public ContactBuilder(){}

        public ContactBuilder owner(User owner){
            this.owner = owner;
            return this;
        }

        public ContactBuilder person(User person){
            this.person = person;
            return this;
        }

        public ContactBuilder confirmed(Boolean isConfirmed) {
            this.isConfirmed = isConfirmed;
            return this;
        }

        public ContactBuilder id(Long id){
            this.id = id;
            return this;
        }

        private void buildDataIntegrityCheck() throws InstantiationException {
            if(owner == null || person == null || isConfirmed == null){
                throw new InstantiationException(
                        "invalid or not sufficient data for Contact object instantiation");
            }
        }

        public Contact build() throws InstantiationException {
            buildDataIntegrityCheck();
            return new Contact(this);
        }
    }

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

    @Override
    public String toString() {
        return "Contact{" +
                "owner=" + owner +
                ", person=" + person +
                ", isConfirmed=" + isConfirmed +
                '}';
    }
}
