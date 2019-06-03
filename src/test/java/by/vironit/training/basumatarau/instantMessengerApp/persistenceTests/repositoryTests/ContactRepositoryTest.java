package by.vironit.training.basumatarau.instantMessengerApp.persistenceTests.repositoryTests;

import by.vironit.training.basumatarau.instantMessengerApp.model.Contact;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;
import by.vironit.training.basumatarau.instantMessengerApp.repository.ContactRepository;
import by.vironit.training.basumatarau.instantMessengerApp.repository.RoleRepsitory;
import by.vironit.training.basumatarau.instantMessengerApp.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ContactRepositoryTest extends BaseRepositoryTest{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private RoleRepsitory roleRepsitory;

    private Set<User> users = new HashSet<>();

    @Before
    public void init() throws InstantiationException {
        users.add(new User.UserBuilder()
                .role(roleRepsitory.findById(2).get())
                .enabled(true)
                .firstName("TestFirstName1")
                .lastName("TestLastName1")
                .nickName("TestNickName1")
                .email("test@email.com1")
                .passwordHash("testStub1")
                .build());
        users.add(new User.UserBuilder()
                .role(roleRepsitory.findById(2).get())
                .enabled(true)
                .firstName("TestFirstName2")
                .lastName("TestLastName2")
                .nickName("TestNickName2")
                .email("test@email.com2")
                .passwordHash("testStub2")
                .build());
        users.add(new User.UserBuilder()
                .role(roleRepsitory.findById(2).get())
                .enabled(true)
                .firstName("TestFirstName3")
                .lastName("TestLastName3")
                .nickName("TestNickName3")
                .email("test@email.com3")
                .passwordHash("testStub3")
                .build());
        users.forEach(
                user -> userRepository.saveAndFlush(user)
        );
    }

    @After
    public void clean(){
        users.clear();
    }

    @Test
    public void whenUserHasContacts_thenGetContactDetails(){

    }

    @Test
    public void whenUserAddedContacts_thenAssertTheNewContactsPersisted()
            throws InstantiationException {
        final User owner = users.stream().findAny().orElseThrow(
                () -> new RuntimeException("before test condition has not been met - collection empty"));
        users.remove(owner);
        final Set<Contact> contacts = new HashSet<>();
        for (User contactPerson : users) {
            contacts.add(new Contact.ContactBuilder()
                    .owner(owner)
                    .person(contactPerson)
                    .confirmed(true)
                    .build()
            );
        }
        owner.setContacts(contacts);
        userRepository.saveAndFlush(owner);

        final User retrieved = userRepository.findByEmail(owner.getEmail());
        assertThat(retrieved).isEqualTo(owner);
    }

}
