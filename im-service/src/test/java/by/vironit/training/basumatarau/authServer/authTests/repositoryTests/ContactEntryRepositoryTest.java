package by.vironit.training.basumatarau.authServer.authTests.repositoryTests;

import by.vironit.training.basumatarau.messengerService.model.Contact;
import by.vironit.training.basumatarau.messengerService.model.ContactEntry;
import by.vironit.training.basumatarau.messengerService.model.User;
import org.junit.Before;
import org.junit.Test;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ContactEntryRepositoryTest extends BaseRepositoryTest{

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
        owner.getContactEntries().addAll(contacts);
        userRepository.save(owner);
    }

    @Test
    public void whenUserHasContact_thenGetContactDetails(){
        final User retrievedUserByEmail = userRepository.findUserWithContactsByEmail(owner.getEmail())
                .orElseThrow(()-> new RuntimeException("failure to fetch any user"));

        assertThat(retrievedUserByEmail.getRole()).isNotNull();

        final Set<ContactEntry> contacts = retrievedUserByEmail.getContactEntries();
        assertThat(contacts.isEmpty()).isFalse();
        contacts.forEach(contact -> {
            assertThat(contact.getOwner().equals(owner)).isTrue();
        });
    }

    @Test
    public void whenUserAddedContacts_thenTheNewContactsPersistedCorrectly()
            throws InstantiationException {
        final User retrieved = userRepository.findByEmail(owner.getEmail())
                .orElseThrow(()-> new RuntimeException("failure to fetch any user"));
        assertThat(retrieved).isEqualTo(owner);
    }

}
