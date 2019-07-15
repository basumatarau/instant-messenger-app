package by.vironit.training.basumatarau.authServer.authTests.repositoryTests;

import by.vironit.training.basumatarau.messengerService.model.Message;
import by.vironit.training.basumatarau.messengerService.model.Subscription;
import by.vironit.training.basumatarau.messengerService.repository.DistributedMessageRepository;
import by.vironit.training.basumatarau.messengerService.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.awt.print.Pageable;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DistributedMessageRepositoryTest extends ChatRoomRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DistributedMessageRepository distributedMessageRepository;

    @Test
    public void whenDistributedMessagedPersisted_thenDistributedMessageCanBeReadBySubscribers() throws Exception {
        final Subscription subscr = userRepository.findUserWithContactEntriesByEmail(admin.getEmail())
                .orElseThrow(()->new RuntimeException("failed to fetch any user")).getContactEntries()
                .stream()
                .filter(contactEntry -> contactEntry instanceof Subscription)
                .map(contactEntry -> ((Subscription) contactEntry))
                .findAny()
                .orElseThrow(() -> new Exception("user has no subscriptions"));

        final List<Message> messages
                = distributedMessageRepository.findByChatRoom(subscr.getChatRoom());
        assertThat(messages).isNotNull();
        for (Message message : messages) {
            System.out.println(message.getBody());
        }
        final PageRequest pageable = PageRequest.of(0, 10);
        final Slice<Message> slice =
                distributedMessageRepository.findByChatRoom(subscr.getChatRoom(), pageable);

        assertThat(slice.getNumberOfElements()).isNotNull();
        for (Message message : slice.getContent()) {
            System.out.println(message);
        }
    }
}
