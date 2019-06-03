package by.vironit.training.basumatarau.instantMessengerApp.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class MessageResource {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "messageresource_identity_generator")
    @SequenceGenerator(name = "messageresource_identity_generator",
            sequenceName = "messageresources_id_seq",
            allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MessageResource(){}

    protected MessageResource(MessageResourceBuilder builder){
        this.name = builder.name;
    }

    public abstract static class MessageResourceBuilder
            <R extends MessageResource, B extends MessageResourceBuilder<R, B>>{
        private String name;

        public MessageResourceBuilder(){}

        /**here and below: returned values are extensions of the base type "B"
         * (which is curiously recurred template impl.) */
        @SuppressWarnings("unchecked")
        public B name(String name){
            this.name = name;
            return ((B) this);
        }

        public abstract R build() throws InstantiationException;

        protected void buildDataIntegrityCheck() throws InstantiationException {
            if(name == null){
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
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
