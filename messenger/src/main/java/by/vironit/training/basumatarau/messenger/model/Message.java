package by.vironit.training.basumatarau.messenger.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "messages", schema = "instant_messenger_db_schema")
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
            schema = "instant_messenger_db_schema",
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

    @OneToMany(mappedBy = "message",
            orphanRemoval = true,
            cascade = CascadeType.PERSIST)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<MessageResource> resources;

    public Message(){}

    protected Message(MessageBuilder builder){
        this.body = builder.body;
        this.author = builder.author;
        this.timeSent =  builder.timeSent;
        this.resources = builder.resources;
    }

    public static abstract class MessageBuilder
            <M extends Message, B extends MessageBuilder<M, B>>{
        private String body;
        private User author;
        private Long timeSent;
        private Set<MessageResource> resources = new LinkedHashSet<>();

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
        public B messageResources(Set<MessageResource> resources){
            this.resources = resources;
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

    public Set<MessageResource> getResources() {
        return resources;
    }

    public void setResources(Set<MessageResource> resources) {
        this.resources = resources;
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
