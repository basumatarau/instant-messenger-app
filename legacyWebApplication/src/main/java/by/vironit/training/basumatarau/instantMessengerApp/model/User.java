package by.vironit.training.basumatarau.instantMessengerApp.model;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class User {
    private Long id;
    private Role role;
    private String firstName;
    private String lastName;
    private String nickName;
    private String email;
    private String passwordHash;
    private Boolean isEnabled;
    private Set<Contact> contacts = new LinkedHashSet<>();
    private byte[] salt;

    public User() {
    }

    private User(UserBuilder builder){
        this.id = builder.id;
        this.salt = builder.salt;
        this.role = builder.role;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.nickName = builder.nickName;
        this.email = builder.email;
        this.passwordHash = builder.passwordHash;
        this.isEnabled = builder.isEnabled;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public static class UserBuilder {
        private Long id;
        private Role role;
        private String firstName;
        private String lastName;
        private String nickName;
        private String email;
        private String passwordHash;
        private Boolean isEnabled;
        private Set<Contact> contacts;
        private byte[] salt;

        public UserBuilder() {}

        public UserBuilder salt(byte[] salt){
            this.salt = salt;
            return this;
        }

        public UserBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public UserBuilder id(Long id){
            this.id = id;
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
            return new User(this);
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
        return Objects.equals(nickName, user.nickName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(passwordHash, user.passwordHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickName, email, passwordHash);
    }
}
