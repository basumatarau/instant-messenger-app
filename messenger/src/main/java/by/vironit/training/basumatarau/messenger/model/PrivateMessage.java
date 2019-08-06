package by.vironit.training.basumatarau.messenger.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Objects;

@Entity
@DiscriminatorValue(value = Message.PRIVATE_MESSAGE_TYPE)
public class PrivateMessage extends Message {

    @ManyToOne
    @JoinColumn(
            name = "id_contact",
            foreignKey = @ForeignKey)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PersonalContact personalContact;

    public PersonalContact getPersonalContact() {
        return personalContact;
    }

    public void setPersonalContact(PersonalContact personalContact) {
        this.personalContact = personalContact;
    }

    public PrivateMessage() {
    }

    protected PrivateMessage(PrivateMessageBuilder builder) {
        super(builder);
        this.personalContact = builder.personalContact;
    }

    public static class PrivateMessageBuilder
            extends MessageBuilder<PrivateMessage, PrivateMessageBuilder> {

        private PersonalContact personalContact;

        public PrivateMessageBuilder contact(PersonalContact personalContact) {
            this.personalContact = personalContact;
            return this;
        }

        @Override
        protected void messageBuildIntegrityCheck() throws InstantiationException {
            super.messageBuildIntegrityCheck();
            if (personalContact == null) {
                throw new InstantiationException(
                        "invalid or not sufficient data for " +
                                getClass().getName() +
                                " object instantiation: addressee has not been assigned");
            }
        }

        @Override
        public PrivateMessage build() throws InstantiationException {
            messageBuildIntegrityCheck();
            return new PrivateMessage(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PrivateMessage that = (PrivateMessage) o;
        return Objects.equals(personalContact, that.personalContact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), personalContact);
    }
}
