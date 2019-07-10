package by.vironit.training.basumatarau.authServer.authTests.repositoryTests;

import by.vironit.training.basumatarau.messengerService.model.Contact;
import by.vironit.training.basumatarau.messengerService.model.Message;
import by.vironit.training.basumatarau.messengerService.model.PrivateMessage;
import by.vironit.training.basumatarau.messengerService.model.User;
import by.vironit.training.basumatarau.messengerService.repository.ContactRepository;
import by.vironit.training.basumatarau.messengerService.repository.PrivateMessageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.InitializationError;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PrivateMessageRepositoryTest extends BaseRepositoryTest {
    @Autowired
    private PrivateMessageRepository messageRepository;

    @Autowired
    private ContactRepository contactRepository;

    private User messageSender;
    private User messageReceiver;

    @Before
    public void pmRepositoryTestInit() throws InitializationError, InstantiationException {
        messageSender = users.stream().findAny().orElseThrow(
                () -> new InitializationError("test case setup failure"));
        users.remove(messageSender);
        messageReceiver = users.stream().findAny().orElseThrow(
                () -> new InitializationError("test case setup failure"));

        final Contact contact = new Contact.ContactBuilder()
                .confirmed(true)
                .owner(messageSender)
                .person(messageReceiver)
                .build();
        final Contact contactCounter = new Contact.ContactBuilder()
                .confirmed(true)
                .owner(messageReceiver)
                .person(messageSender)
                .build();
        messageSender.getContactEntries().add(contact);
        messageReceiver.getContactEntries().add(contactCounter);

        userRepository.saveAndFlush(messageSender);
        userRepository.saveAndFlush(messageReceiver);

        final PrivateMessage message = new PrivateMessage.PrivateMessageBuilder()
                .author(messageSender)
                .body("test message")
                .contact(contactRepository.findContactByOwnerIdAndPersonId(
                        messageSender.getId(), messageReceiver.getId())
                        .orElseThrow(
                                () -> new InitializationError("test case setup failure")))
                .timeSent(new Date().toInstant().toEpochMilli())
                .build();
        messageRepository.saveAndFlush(message);
    }

    @Test
    public void whenPrivateMessagePersisted_thenFindMessage() throws Exception {
        final User sender = userRepository.findUserWithContactsByEmail(messageSender.getEmail())
                .orElseThrow(()-> new RuntimeException("failure to fetch any user"));
        final Contact contact = sender.getContactEntries()
                .stream()
                .filter(contactEntry -> contactEntry instanceof Contact)
                .map(contactEntry -> ((Contact) contactEntry))
                .findAny()
                .orElseThrow(
                        () -> new Exception("contact has not been persisted"));
        final List<Message> messagesForContact = messageRepository.findByContact(contact);
        assertThat(messagesForContact).isNotNull();
        for (Message message : messagesForContact) {
            System.out.print(message.getTimeSent() + " ");
            System.out.println(message.getBody());
        }

    }

}
