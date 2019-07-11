package by.vironit.training.basumatarau.simpleMessengerApp.model;

import java.util.Objects;

public class PrivateMessage extends Message {
    private Contact contact;

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public PrivateMessage() {
    }

    protected PrivateMessage(PrivateMessageBuilder builder) {
        super(builder);
        this.contact = builder.contact;
    }

    public static class PrivateMessageBuilder
            extends MessageBuilder<PrivateMessage, PrivateMessageBuilder> {

        private Contact contact;

        public PrivateMessageBuilder contact(Contact contact) {
            this.contact = contact;
            return this;
        }

        @Override
        protected void messageBuildIntegrityCheck() throws InstantiationException {
            super.messageBuildIntegrityCheck();
            if (contact == null) {
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
        return Objects.equals(contact, that.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), contact);
    }
}
