package by.vironit.training.basumatarau.instantMessengerApp.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "messages")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "messagetype", discriminatorType=DiscriminatorType.STRING)
public abstract class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "message_identity_generator")
    @SequenceGenerator(name = "message_identity_generator",
            sequenceName = "messages_id_seq",
            //schema = "instant_messenger_db_schema",
            allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "body", nullable = false)
    private String body;

    @ManyToOne
    @JoinColumn(name = "id_author",
            foreignKey = @ForeignKey,
            nullable = false)
    private User author;

    @Column(name = "timesent", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeSent;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(
            name = "id_messageresource",
            foreignKey = @ForeignKey)
    private MessageResource resource;

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

    public MessageResource getResource() {
        return resource;
    }

    public void setResource(MessageResource resource) {
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
