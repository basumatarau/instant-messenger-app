package by.vironit.training.basumatarau.instantMessengerApp.persistenceTests.repositoryTests;

import by.vironit.training.basumatarau.instantMessengerApp.model.Contact;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ContactRepositoryTest extends BaseRepositoryTest{

    private User owner;

    @Before
    public void initContactRepoTest() throws InstantiationException {
        owner = users.stream().findAny().orElseThrow(
                () -> new RuntimeException("before test condition has not been met - collection empty"));
        users.remove(owner);
        final Set<Contact> contacts = new HashSet<>();
        for (User contactPerson : users) {
            contacts.add(new Contact.ContactBuilder()
                    .owner(owner)
                    .person(contactPerson)
                    .confirmed(false)
                    .build()
            );
        }
        owner.getContacts().addAll(contacts);
        userRepository.save(owner);
    }
    @After
    public void cleanContactRepoTestO() throws InstantiationException{
        users.add(owner);
    }

    @Test
    public void whenUserHasContact_thenGetContactDetails(){
        final User retrievedUserByEmail = userRepository.findUserWithContatsByEmail(owner.getEmail());
        assertThat(retrievedUserByEmail.getRole()).isNotNull();
        final Set<Contact> contacts = retrievedUserByEmail.getContacts();
        assertThat(contacts.isEmpty()).isFalse();
        contacts.forEach(contact -> {
            assertThat(contact.getOwner().equals(owner)).isTrue();
            assertThat(contact.getPerson().equals(owner)).isFalse();
            assertThat(contact.getIsConfirmed()).isNotNull();
        });
    }

    @Test
    public void whenUserAddedContacts_thenTheNewContactsPersistedCorrectly()
            throws InstantiationException {
        final User retrieved = userRepository.findByEmail(owner.getEmail());
        assertThat(retrieved).isEqualTo(owner);
    }

}
