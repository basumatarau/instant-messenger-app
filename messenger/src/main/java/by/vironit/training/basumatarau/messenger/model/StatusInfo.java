package by.vironit.training.basumatarau.messenger.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "message_status_info", schema = "instant_messenger_db_schema")
public class StatusInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "message_status_info_identity_generator")
    @SequenceGenerator(name = "message_status_info_identity_generator",
            sequenceName = "message_status_info_id_seq",
            schema = "instant_messenger_db_schema",
            allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "delivered", nullable = false)
    private Boolean delivered = Boolean.FALSE;

    @Column(name = "time_delivered")
    private Long timeDelivered;

    @Column(name = "read", nullable = false)
    private Boolean read = Boolean.FALSE;

    @Column(name = "time_read")
    private Long timeRead;

    @ManyToOne
    @JoinColumn(
            name = "id_contact_entry",
            nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ContactEntry contactEntry;

    @ManyToOne
    @JoinColumn(
            name = "id_message",
            nullable = false)
    private Message message;

    public StatusInfo(){}

    public StatusInfo(StatusInfoBuilder builder){
        this.delivered = builder.delivered;
        this.timeDelivered = builder.timeDelivered;
        this.read = builder.read;
        this.timeRead = builder.timeRead;
        this.contactEntry = builder.contactEntry;
        this.message = builder.message;
    }

    public static class StatusInfoBuilder {
        private Boolean delivered;
        private Long timeDelivered;
        private Boolean read;
        private Long timeRead;
        private ContactEntry contactEntry;
        private Message message;

        public StatusInfoBuilder() {
        }

        public StatusInfoBuilder delivered(Boolean delivered) {
            this.delivered = delivered;
            return this;
        }

        public StatusInfoBuilder timeDelivered(Long timeDelivered) {
            this.timeDelivered = timeDelivered;
            return this;
        }

        public StatusInfoBuilder read(Boolean read) {
            this.read = read;
            return this;
        }

        public StatusInfoBuilder timeRead(Long timeRead) {
            this.timeRead = timeRead;
            return this;
        }

        public StatusInfoBuilder contactEntry(ContactEntry contactEntry){
            this.contactEntry = contactEntry;
            return this;
        }

        public StatusInfoBuilder message(Message message){
            this.message = message;
            return this;
        }

        private void buildDataIntegrityCheck(){
            if (this.contactEntry == null
                    || this.message == null
                    || this.delivered == null
                    || this.read == null) {

                final InstantiationException e = new InstantiationException(
                        "invalid or not sufficient data for UserController object instantiation");

                throw new RuntimeException(e);
            }
        }

        public StatusInfo build(){
            buildDataIntegrityCheck();
            return new StatusInfo(this);
        }
    }

    public Long getId() {
        return id;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public Long getTimeDelivered() {
        return timeDelivered;
    }

    public void setTimeDelivered(Long timeDelivered) {
        this.timeDelivered = timeDelivered;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Long getTimeRead() {
        return timeRead;
    }

    public void setTimeRead(Long timeRead) {
        this.timeRead = timeRead;
    }

    public ContactEntry getContactEntry() {
        return contactEntry;
    }

    public void setContactEntry(ContactEntry contactEntry) {
        this.contactEntry = contactEntry;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusInfo that = (StatusInfo) o;
        return  Objects.equals(contactEntry, that.contactEntry) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactEntry, message);
    }
}
