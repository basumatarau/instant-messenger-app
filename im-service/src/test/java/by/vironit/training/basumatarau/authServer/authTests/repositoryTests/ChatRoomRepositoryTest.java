package by.vironit.training.basumatarau.authServer.authTests.repositoryTests;

import by.vironit.training.basumatarau.messengerService.model.ChatRoom;
import by.vironit.training.basumatarau.messengerService.model.ChatRoomPrivilege;
import by.vironit.training.basumatarau.messengerService.model.Subscription;
import by.vironit.training.basumatarau.messengerService.model.User;
import by.vironit.training.basumatarau.messengerService.repository.ChatRoomPrivilegeRepository;
import by.vironit.training.basumatarau.messengerService.repository.ChatRoomRepository;
import by.vironit.training.basumatarau.messengerService.repository.SubscriptionRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.InitializationError;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class ChatRoomRepositoryTest extends BaseRepositoryTest {
    @Autowired
    protected ChatRoomRepository chatRoomRepository;

    @Autowired
    protected SubscriptionRepository subscriptionRepository;

    @Autowired
    protected ChatRoomPrivilegeRepository chatRoomPrivilegeRepository;

    protected User admin;
    protected Subscription testedSubscription;

    @Before
    public void initChatRoomRepoTest() throws InstantiationException, InitializationError {

        admin = users.stream().findAny().orElseThrow(
                () -> new InitializationError("test data init failure"));

        users.remove(admin);

        final ChatRoomPrivilege adminPrivilege =
                chatRoomPrivilegeRepository.findByName("CHATADMIN");

        final ChatRoom chatRoom =
                chatRoomRepository
                        .findAll()
                        .stream()
                        .findAny()
                        .orElseThrow(() -> new RuntimeException("failed to find any chat room..."));

        testedSubscription = new Subscription.SubscriptionBuilder()
                .enteredChat(new Date())
                .privilege(adminPrivilege)
                .owner(admin)
                .enabled(true)
                .chatRoom(chatRoom)
                .build();

        subscriptionRepository.saveAndFlush(
                testedSubscription
        );

        for (User user : users) {
            subscriptionRepository.saveAndFlush(
                    new Subscription.SubscriptionBuilder()
                            .enteredChat(new Date())
                            .privilege(chatRoomPrivilegeRepository.findByName("COMMONER"))
                            .owner(user)
                            .enabled(true)
                            .chatRoom(chatRoom)
                            .build()
            );
        }
    }

    @After
    public void cleanChatRoomRepoTest() {
        subscriptionRepository.deleteAll();
    }

    @Test
    public void whenUserSubscribedToChatRoom_thenTheUserSeesEveryBodyInTheRoom() {
        final User user = userRepository.findUserWithSubscriptionsByEmail(
                admin.getEmail()).orElseThrow(() -> new RuntimeException("failure to fetch any user")
        );

        assertThat(user.getContactEntries().contains(testedSubscription)).isTrue();
    }
}
