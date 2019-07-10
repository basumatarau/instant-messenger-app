package by.vironit.training.basumatarau.authServer.authTests.repositoryTests;

import by.vironit.training.basumatarau.messengerService.model.ChatRoom;
import by.vironit.training.basumatarau.messengerService.model.ChatRoomPrivilege;
import by.vironit.training.basumatarau.messengerService.model.Subscription;
import by.vironit.training.basumatarau.messengerService.model.User;
import by.vironit.training.basumatarau.messengerService.repository.ChatRoomPrivilegeRepository;
import by.vironit.training.basumatarau.messengerService.repository.ChatRoomRepository;
import by.vironit.training.basumatarau.messengerService.repository.SubscriptionRepository;
import by.vironit.training.basumatarau.messengerService.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class SubscriptionRepositoryTest extends BaseRepositoryTest{
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatRoomPrivilegeRepository chatRoomPrivilegeRepository;

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

        final ChatRoomPrivilege chatAdminPrivilege = chatRoomPrivilegeRepository.findByName("CHATADMIN");
        final Subscription adminSubscription = new Subscription.SubscriptionBuilder()
                .chatRoom(anyChatRoom)
                .owner(anyUser)
                .enabled(true)
                .enteredChat(new Date())
                .privilege(chatAdminPrivilege)
                .build();
        subscriptionRepository.save(adminSubscription);

        assertThat(userRepository
                .findUserWithSubscriptionsByEmail(anyUser.getEmail())
                .orElseThrow(()-> new RuntimeException("failure to fetch any user"))
                .getContactEntries()
                .contains(adminSubscription)).isTrue();
    }
}
