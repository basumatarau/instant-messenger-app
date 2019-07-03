package by.vironit.training.basumatarau.instantMessengerApp.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "contactEntries", schema = "instant_messenger_db_schema")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "entryType", discriminatorType=DiscriminatorType.STRING)
public abstract class ContactEntry {
    static final String SUBSCRIPTION_TYPE = "subs";
    static final String PERSONAL_CONTACT_TYPE = "cont";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "contactEntry_identity_generator")
    @SequenceGenerator(name = "contactEntry_identity_generator",
            sequenceName = "contactEntries_id_seq",
            schema = "instant_messenger_db_schema",
            allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_owner", nullable = false)
    private User owner;

    public ContactEntry(){}

    protected ContactEntry(ContactEntryBuilder builder) {
        this.owner = builder.owner;
    }

    public static abstract class ContactEntryBuilder
            <C extends ContactEntry, B extends ContactEntry.ContactEntryBuilder<C, B>>{
        private User owner;

        protected ContactEntryBuilder(){}

        /**here and below: returned values are extensions of the base type "B"
         * (which is curiously recurred template impl.) */
        @SuppressWarnings("unchecked")
        public B owner(User owner){
            this.owner = owner;
            return ((B) this);
        }

        public abstract C build() throws InstantiationException;

        protected void contactEntryBuildIntegrityCheck() throws InstantiationException{
            if(owner == null){
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        //todo fix the convenience method
        //owner.getContactEntries().add(this);
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactEntry that = (ContactEntry) o;
        return owner.equals(that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner);
    }
}
