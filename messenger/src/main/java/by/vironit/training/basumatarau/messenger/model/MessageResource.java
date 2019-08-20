package by.vironit.training.basumatarau.messenger.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "message_resources", schema = "instant_messenger_db_schema")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "resource_type", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value = MessageResource.PLAIN_FILE_RESOURCE)
public class MessageResource {

    static final String PLAIN_FILE_RESOURCE = "file";
    static final String IMAGE_RESOURCE = "image";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "message_resource_identity_generator")
    @SequenceGenerator(name = "message_resource_identity_generator",
            sequenceName = "message_resource_id_seq",
            schema = "instant_messenger_db_schema",
            allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "binData",
            columnDefinition = "bytea NOT NULL",
            nullable = false)
    private byte[] binData;

    @ManyToOne
    @JoinColumn(name = "id_message",
            nullable = false)
    private Message message;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public byte[] getBinData() {
        return binData;
    }

    public void setBinData(byte[] binData) {
        this.binData = binData;
    }

    public MessageResource(){}

    protected MessageResource(MessageResourceBuilder builder){
        this.name = builder.name;
        this.message = builder.message;
        this.binData = builder.data;
    }

    public static class MessageResourceBuilder
            <R extends MessageResource, B extends MessageResourceBuilder<R, B>>{
        private String name;
        private Message message;
        private byte[] data;

        public MessageResourceBuilder(){}

        /**here and below: returned values are extensions of the base type "B"
         * (which is curiously recurred template impl.) */
        @SuppressWarnings("unchecked")
        public B name(String name){
            this.name = name;
            return ((B) this);
        }

        @SuppressWarnings("unchecked")
        public B message(Message message){
            this.message = message;
            return ((B) this);
        }

        @SuppressWarnings("unchecked")
        public B data(byte[] data){
            this.data = data;
            return ((B) this);
        }

        /**
         * R is parent type of the returned value;
         */
        @SuppressWarnings("unchecked")
        public R build() throws InstantiationException{
            buildDataIntegrityCheck();
            return (R) new MessageResource(this);
        }

        protected void buildDataIntegrityCheck() throws InstantiationException {
            if(name == null || message == null || data == null){
                throw new InstantiationException(
                        "invalid or not sufficient data for " +
                                getClass().getName() +
                                " object instantiation");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageResource that = (MessageResource) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, message);
    }
}
