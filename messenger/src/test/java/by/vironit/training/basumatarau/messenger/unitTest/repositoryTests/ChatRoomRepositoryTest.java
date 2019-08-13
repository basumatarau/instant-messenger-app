package by.vironit.training.basumatarau.messenger.unitTest.repositoryTests;

import by.vironit.training.basumatarau.messenger.model.ChatRoom;
import by.vironit.training.basumatarau.messenger.model.Subscription;
import by.vironit.training.basumatarau.messenger.model.User;
import by.vironit.training.basumatarau.messenger.repository.ChatRoomRepository;
import by.vironit.training.basumatarau.messenger.repository.ContactEntryRepository;
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
    protected ContactEntryRepository contactEntryRepository;

    protected User admin;
    protected Subscription testedSubscription;

    @Before
    public void initChatRoomRepoTest() throws InstantiationException, InitializationError {

        admin = users.stream().findAny().orElseThrow(
                () -> new InitializationError("test data init failure"));

        users.remove(admin);

        final ChatRoom chatRoom =
                chatRoomRepository
                        .findAll()
                        .stream()
                        .findAny()
                        .orElseThrow(() -> new RuntimeException("failed to find any chat room..."));

        testedSubscription = new Subscription.SubscriptionBuilder()
                .enteredChat(new Date().getTime())
                .privilege(Subscription.ChatRoomPrivilege.CHATADMIN)
                .owner(admin)
                .enabled(true)
                .chatRoom(chatRoom)
                .build();

        contactEntryRepository.saveAndFlush(
                testedSubscription
        );

        for (User user : users) {
            contactEntryRepository.saveAndFlush(
                    new Subscription.SubscriptionBuilder()
                            .enteredChat(new Date().getTime())
                            .privilege(Subscription.ChatRoomPrivilege.COMMONER)
                            .owner(user)
                            .enabled(true)
                            .chatRoom(chatRoom)
                            .build()
            );
        }
    }

    @After
    public void cleanChatRoomRepoTest() {
        contactEntryRepository.deleteAll();
    }

    @Test
    public void whenUserSubscribedToChatRoom_thenTheUserSeesEveryBodyInTheRoom() {
        final User user = userRepository.findUserWithSubscriptionsByEmail(
                admin.getEmail()).orElseThrow(() -> new RuntimeException("failure to fetch any user")
        );

        assertThat(user.getContactEntries().contains(testedSubscription)).isTrue();
    }
}
