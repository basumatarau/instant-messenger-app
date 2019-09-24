package by.vironit.training.basumatarau.messenger.model;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@SQLDelete(
        sql = "update instant_messenger_db_schema.users set enabled=false where id = ? ",
        check = ResultCheckStyle.COUNT)
@Where(clause = "enabled=true ")
@Table(name = "users", schema = "instant_messenger_db_schema")
@DynamicUpdate
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "user_identity_generator")
    @SequenceGenerator(name = "user_identity_generator",
            sequenceName = "users_id_seq",
            schema = "instant_messenger_db_schema",
            allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "role", updatable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "nickname", nullable = false)
    private String nickName;

    @Column(name = "email", nullable = false, updatable = false)
    private String email;

    @Column(name = "passwordhash", nullable = false)
    private String passwordHash;

    @Column(name = "enabled", nullable = false)
    private Boolean isEnabled;

    @Enumerated(EnumType.STRING)
    @Column(name = "authprovider", nullable = false, updatable = false)
    private AuthProvider authProvider;

    @OneToMany(mappedBy = "owner",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<ContactEntry> contactEntries = new LinkedHashSet<>();

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "image_url")
    private String imageUrl;

    @PreRemove
    private void deleteUser(){
        this.isEnabled = false;
    }

    public User() {
    }

    private User(UserBuilder builder){
        this.role = builder.role;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.nickName = builder.nickName;
        this.email = builder.email;
        this.passwordHash = builder.passwordHash;
        this.isEnabled = builder.isEnabled;
        this.contactEntries = builder.contactEntries;
        this.providerId = builder.providerId;
        this.imageUrl = builder.imageUrl;
        this.authProvider = builder.authProvider;
    }

    public enum UserRole{
        USER, ADMIN
    }

    public enum AuthProvider{
        local, google, github
    }

    public Long getId() {
        return id;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
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

    public void setActive(Boolean active) {
        isEnabled = active;
    }

    public Set<ContactEntry> getContactEntries() {
        return contactEntries;
    }

    public void setContactEntries(Set<ContactEntry> contactEntries) {
        this.contactEntries = contactEntries;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    public static class UserBuilder {
        private UserRole role;
        private AuthProvider authProvider;
        private String imageUrl;
        private String firstName;
        private String lastName;
        private String nickName;
        private String email;
        private String passwordHash;
        private String providerId;
        private Boolean isEnabled;
        private Set<ContactEntry> contactEntries = new LinkedHashSet<>();

        public UserBuilder() {}

        public UserBuilder authProvider(AuthProvider authProvider){
            this.authProvider = authProvider;
            return this;
        }

        public UserBuilder imageUrl(String imageUrl){
            this.imageUrl = imageUrl;
            return this;
        }

        public UserBuilder providerId(String providerId){
            this.providerId = providerId;
            return this;
        }

        public UserBuilder role(UserRole role) {
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

        public UserBuilder contactEntries(Set<ContactEntry> contactEntries) {
            this.contactEntries = contactEntries;
            return this;
        }

        public User build() {
            buildDataIntegrityCheck();
            return new User(this);
        }

        private void buildDataIntegrityCheck(){
            if (this.email == null
                    || this.authProvider == null
                    || this.nickName == null
                    || this.role == null
                    || this.isEnabled == null
                    || this.contactEntries == null) {

                final InstantiationException e = new InstantiationException(
                        "invalid or not sufficient data for UserController object instantiation");

                throw new RuntimeException(e);
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
                Objects.equals(authProvider, user.authProvider) &&
                Objects.equals(passwordHash, user.passwordHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, firstName, lastName, nickName, email, passwordHash, authProvider);
    }
}
