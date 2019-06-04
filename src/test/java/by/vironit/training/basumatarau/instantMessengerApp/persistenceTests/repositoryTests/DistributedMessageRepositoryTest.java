package by.vironit.training.basumatarau.instantMessengerApp.persistenceTests.repositoryTests;

import by.vironit.training.basumatarau.instantMessengerApp.model.Message;
import by.vironit.training.basumatarau.instantMessengerApp.model.Subscription;
import by.vironit.training.basumatarau.instantMessengerApp.repository.DistributedMessageRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DistributedMessageRepositoryTest extends ChatRoomRepositoryTest {
    @Autowired
    private DistributedMessageRepository distributedMessageRepository;

    @Test
    public void whenDistributedMessagedPersisted_thenDistributedMessageCanBeReadBySubscribers() throws Exception {
        final Subscription subscr = admin.getSubscriptions()
                .stream()
                .findAny()
                .orElseThrow(() -> new Exception("user has no subscriptions"));
        final List<Message> messages
                = distributedMessageRepository.findByChatRoom(subscr.getChatRoom());
        assertThat(messages).isNotNull();
        for (Message message : messages) {
            System.out.println(message.getBody());
        }
    }
}
