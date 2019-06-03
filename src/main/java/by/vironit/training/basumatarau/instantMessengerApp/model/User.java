package by.vironit.training.basumatarau.instantMessengerApp.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "user_identity_generator")
    @SequenceGenerator(name = "user_identity_generator",
            sequenceName = "users_id_seq",
            //schema = "instant_messenger_db_schema",
            allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "id_role",
            nullable = false)
    private Role role;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "nickname", nullable = false)
    private String nickName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "passwordhash", nullable = false)
    private String passwordHash;

    @Column(name = "enabled", nullable = false)
    private Boolean isEnabled;

    @OneToMany(mappedBy = "owner",
            orphanRemoval = true,
            cascade = {CascadeType.ALL})
    private Set<Contact> contacts;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Boolean getActive() {
        return isEnabled;
    }

    public void setActive(Boolean active) {
        isEnabled = active;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public static class UserBuilder {
        private Role role;
        private String firstName;
        private String lastName;
        private String nickName;
        private String email;
        private String passwordHash;
        private Boolean isEnabled;
        private Set<Contact> contacts;

        public UserBuilder() {}

        public UserBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }


        public UserBuilder nickName(String nickName) {
            this.nickName = nickName;
            return this;
        }


        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder passwordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public UserBuilder enabled(Boolean isEnabled) {
            this.isEnabled = isEnabled;
            return this;
        }

        public UserBuilder contacts(Set<Contact> contacts) {
            this.contacts = contacts;
            return this;
        }

        public User build() throws InstantiationException {
            buildDataIntegrityCheck();
            User user = new User();
            if (firstName != null) {
                user.setFirstName(firstName);
            }
            if (lastName != null) {
                user.setLastName(lastName);
            }
            if (nickName != null) {
                user.setNickName(nickName);
            }
            user.setEmail(email);
            user.setNickName(nickName);
            user.setRole(role);
            user.setPasswordHash(passwordHash);
            user.setActive(isEnabled);
            return user;
        }

        private void buildDataIntegrityCheck() throws InstantiationException {
            if (this.email == null
                    || this.passwordHash == null
                    || this.nickName == null
                    || this.role == null
                    || this.isEnabled == null) {
                throw new InstantiationException(
                        "invalid or not sufficient data for User object instantiation");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(role, user.role) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(nickName, user.nickName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(passwordHash, user.passwordHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, firstName, lastName, nickName, email, passwordHash);
    }
}
