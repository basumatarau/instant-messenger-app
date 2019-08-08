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

    @OneToOne(
            mappedBy = "message",
            cascade = {CascadeType.ALL})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private StatusInfo delivery;

    public PersonalContact getPersonalContact() {
        return personalContact;
    }

    public void setPersonalContact(PersonalContact personalContact) {
        this.personalContact = personalContact;
    }

    public StatusInfo getDelivery() {
        return delivery;
    }

    public void setDelivery(StatusInfo delivery) {
        this.delivery = delivery;
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
        private StatusInfo delivery;

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

            final PrivateMessage newPrivateMessage = new PrivateMessage(this);

            final StatusInfo deliveryStatus = new StatusInfo.StatusInfoBuilder()
                    .contactEntry(this.personalContact)
                    .message(newPrivateMessage)
                    .read(false)
                    .delivered(false)
                    .build();

            newPrivateMessage.setDelivery(deliveryStatus);

            return newPrivateMessage;
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
