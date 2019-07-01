package by.vironit.training.basumatarau.instantMessengerApp.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "messages")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "messagetype", discriminatorType=DiscriminatorType.STRING)
public abstract class Message {

    static final String DISTRIBUTED_MESSAGE_TYPE = "dm";
    static final String PRIVATE_MESSAGE_TYPE = "pm";

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
    private Long timeSent;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(
            name = "id_messageresource",
            foreignKey = @ForeignKey)
    private MessageResource resource;

    public Message(){}

    protected Message(MessageBuilder builder){
        this.body = builder.body;
        this.author = builder.author;
        this.timeSent =  builder.timeSent;
        this.resource = builder.messageResource;
    }

    public static abstract class MessageBuilder
            <M extends Message, B extends MessageBuilder<M, B>>{
        private String body;
        private User author;
        private Long timeSent;
        private MessageResource messageResource;

        protected MessageBuilder(){}

        /**here and below: returned values are extensions of the base type "B"
         * (which is curiously recurred template impl.) */
        @SuppressWarnings("unchecked")
        public B body(String body){
            this.body = body;
            return ((B) this);
        }

        /**see also {@link #body(String)} */
        @SuppressWarnings("unchecked")
        public B author(User author){
            this.author = author;
            return ((B) this);
        }

        /**see also {@link #body(String)} */
        @SuppressWarnings("unchecked")
        public B timeSent(Long timeSent){
            this.timeSent = timeSent;
            return ((B) this);
        }

        /**see also {@link #body(String)} */
        @SuppressWarnings("unchecked")
        public B messageSource(MessageResource messageResource){
            this.messageResource = messageResource;
            return ((B) this);
        }

        public abstract M build() throws InstantiationException;

        protected void messageBuildIntegrityCheck() throws InstantiationException{
            if(body==null || author == null || timeSent == null){
                throw new InstantiationException(
                        "invalid or not sufficient data for " +
                                getClass().getName() +
                                " object instantiation");
            }
        }
    }

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

    public Long getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Long timeSent) {
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
