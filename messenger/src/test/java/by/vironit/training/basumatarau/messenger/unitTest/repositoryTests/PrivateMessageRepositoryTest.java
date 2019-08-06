package by.vironit.training.basumatarau.messenger.unitTest.repositoryTests;

import by.vironit.training.basumatarau.messenger.model.PersonalContact;
import by.vironit.training.basumatarau.messenger.model.Message;
import by.vironit.training.basumatarau.messenger.model.PrivateMessage;
import by.vironit.training.basumatarau.messenger.model.User;
import by.vironit.training.basumatarau.messenger.repository.PersonalContactRepository;
import by.vironit.training.basumatarau.messenger.repository.PrivateMessageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.InitializationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PrivateMessageRepositoryTest extends BaseRepositoryTest {
    @Autowired
    private PrivateMessageRepository messageRepository;

    @Autowired
    private PersonalContactRepository personalContactRepository;

    private User messageSender;
    private User messageReceiver;

    @Before
    public void pmRepositoryTestInit() throws InitializationError, InstantiationException {
        messageSender = users.stream().findAny().orElseThrow(
                () -> new InitializationError("test case setup failure"));
        users.remove(messageSender);
        messageReceiver = users.stream().findAny().orElseThrow(
                () -> new InitializationError("test case setup failure"));

        final PersonalContact personalContact = new PersonalContact.ContactBuilder()
                .confirmed(true)
                .owner(messageSender)
                .person(messageReceiver)
                .build();
        final PersonalContact personalContactCounter = new PersonalContact.ContactBuilder()
                .confirmed(true)
                .owner(messageReceiver)
                .person(messageSender)
                .build();
        messageSender.getContactEntries().add(personalContact);
        messageReceiver.getContactEntries().add(personalContactCounter);

        userRepository.saveAndFlush(messageSender);
        userRepository.saveAndFlush(messageReceiver);

        final PrivateMessage message = new PrivateMessage.PrivateMessageBuilder()
                .author(messageSender)
                .body("test message")
                .contact(personalContactRepository.findContactByOwnerIdAndPersonId(
                        messageSender.getId(), messageReceiver.getId())
                        .orElseThrow(
                                () -> new InitializationError("test case setup failure")))
                .timeSent(new Date().toInstant().toEpochMilli())
                .build();
        messageRepository.saveAndFlush(message);
    }

    @Test
    public void whenPrivateMessagePersisted_thenFindMessage() throws Exception {
        final User sender = userRepository.findUserWithPersonalContactsByEmail(messageSender.getEmail())
                .orElseThrow(()-> new RuntimeException("failure to fetch any user"));
        final PersonalContact personalContact = sender.getContactEntries()
                .stream()
                .filter(contactEntry -> contactEntry instanceof PersonalContact)
                .map(contactEntry -> ((PersonalContact) contactEntry))
                .findAny()
                .orElseThrow(
                        () -> new Exception("contact has not been persisted"));
        final List<Message> messagesForContact = messageRepository.findByPersonalContact(personalContact);
        assertThat(messagesForContact).isNotNull();
        for (Message message : messagesForContact) {
            System.out.print(message.getTimeSent() + " ");
            System.out.println(message.getBody());
        }

        final PageRequest pageable = PageRequest.of(0, 10);
        final Slice<Message> slice = messageRepository.findByPersonalContact(personalContact, pageable);
        assertThat(slice.getNumberOfElements()).isPositive();
        for (Message message : slice.getContent()) {
            System.out.println(message.getBody());
        }
    }

}
