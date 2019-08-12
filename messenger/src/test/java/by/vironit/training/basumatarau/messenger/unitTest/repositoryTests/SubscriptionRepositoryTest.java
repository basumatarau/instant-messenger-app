package by.vironit.training.basumatarau.messenger.unitTest.repositoryTests;

import by.vironit.training.basumatarau.messenger.model.ChatRoom;
import by.vironit.training.basumatarau.messenger.model.Subscription;
import by.vironit.training.basumatarau.messenger.model.User;
import by.vironit.training.basumatarau.messenger.repository.ChatRoomRepository;
import by.vironit.training.basumatarau.messenger.repository.SubscriptionRepository;
import by.vironit.training.basumatarau.messenger.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class SubscriptionRepositoryTest extends BaseRepositoryTest{
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenSubscriptionPersisted_thenTheSubscriptionInstanceFetchedWithUserEntity()
            throws InstantiationException {
        final ChatRoom anyChatRoom = chatRoomRepository
                .findAll()
                .stream()
                .findAny()
                .orElseThrow(() -> new RuntimeException("failed to fetch any chat room"));
        final User anyUser = users.stream()
                .findAny()
                .orElseThrow(() -> new RuntimeException("failed to fetch any user"));

        final Subscription adminSubscription = new Subscription.SubscriptionBuilder()
                .chatRoom(anyChatRoom)
                .owner(anyUser)
                .enabled(true)
                .enteredChat(new Date().getTime())
                .privilege(Subscription.ChatRoomPrivilege.CHATADMIN)
                .build();
        subscriptionRepository.save(adminSubscription);

        assertThat(userRepository
                .findUserWithSubscriptionsByEmail(anyUser.getEmail())
                .orElseThrow(()-> new RuntimeException("failure to fetch any user"))
                .getContactEntries()
                .contains(adminSubscription)).isTrue();
    }
}
