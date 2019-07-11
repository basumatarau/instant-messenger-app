package by.vironit.training.basumatarau.simpleMessengerApp.model;

import java.util.Date;
import java.util.Objects;

public abstract class Message {

    public static final String DISTRIBUTED_MESSAGE_TYPE = "dm";
    public static final String PRIVATE_MESSAGE_TYPE = "pm";

    private Long id;
    private String body;
    private User author;
    private Date timeSent;
    private MessageResource resource;

    public Message(){}

    protected Message(MessageBuilder builder){
        this.id = builder.id;
        this.body = builder.body;
        this.author = builder.author;
        this.timeSent =  builder.timeSent;
        this.resource = builder.messageResource;
    }

    public static abstract class MessageBuilder
            <M extends Message, B extends MessageBuilder<M, B>>{
        private Long id;
        private String body;
        private User author;
        private Date timeSent;
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
        public B timeSent(Date timeSent){
            this.timeSent = timeSent;
            return ((B) this);
        }

        /**see also {@link #body(String)} */
        @SuppressWarnings("unchecked")
        public B id(Long id){
            this.id = id;
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
