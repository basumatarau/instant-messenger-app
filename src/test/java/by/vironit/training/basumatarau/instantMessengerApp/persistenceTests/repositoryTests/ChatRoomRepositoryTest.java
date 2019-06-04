package by.vironit.training.basumatarau.instantMessengerApp.persistenceTests.repositoryTests;

import by.vironit.training.basumatarau.instantMessengerApp.model.ChatRoom;
import by.vironit.training.basumatarau.instantMessengerApp.model.ChatRoomPrivilege;
import by.vironit.training.basumatarau.instantMessengerApp.model.Subscription;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;
import by.vironit.training.basumatarau.instantMessengerApp.repository.ChatRoomPrivilegeRepository;
import by.vironit.training.basumatarau.instantMessengerApp.repository.ChatRoomRepository;
import by.vironit.training.basumatarau.instantMessengerApp.repository.SubscriberRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.InitializationError;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ChatRoomRepositoryTest extends BaseRepositoryTest{
    @Autowired
    protected ChatRoomRepository chatRoomRepository;

    @Autowired
    protected SubscriberRepository subscriberRepository;

    @Autowired
    protected ChatRoomPrivilegeRepository chatRoomPrivilegeRepository;

    protected User admin;
    protected Subscription testedSubscription;

    @Before
    public void initChatRoomRepoTest() throws InstantiationException, InitializationError {

        final ChatRoom chatRoom
                = new ChatRoom.ChatRoomBuilder()
                .isPublic(true)
                .name("testChatRoomName")
                .timeCreated(new Date())
                .build();
        chatRoomRepository.saveAndFlush(chatRoom);

        admin = users.stream().findAny().orElseThrow(
                () -> new InitializationError("test data init failure"));
        users.remove(admin);

        final ChatRoomPrivilege adminPrivilege = chatRoomPrivilegeRepository.findByName("CHATADMIN");
        testedSubscription = new Subscription.SubscriberBuilder()
                .enteredChat(new Date())
                .priviledge(adminPrivilege)
                .user(admin)
                .enabled(true)
                .chatRoom(chatRoom)
                .build();

        subscriberRepository.saveAndFlush(
                testedSubscription
        );

        for (User user : users) {
            subscriberRepository.saveAndFlush(
                    new Subscription.SubscriberBuilder()
                            .enteredChat(new Date())
                            .priviledge(chatRoomPrivilegeRepository.findByName("COMMONER"))
                            .user(user)
                            .enabled(true)
                            .chatRoom(chatRoom)
                            .build()
            );
        }
    }

    @After
    public void cleanChatRoomRepoTest(){
        users.clear();
    }

    @Test
    public void whenUserSubscribedToChatRoom_thenTheUserSeesEveryBodyInTheRoom(){
        final User user = userRepository.findByEmail(admin.getEmail());
        final Set<Subscription> subscriptions = user.getSubscriptions();
        assertThat(subscriptions).isNotNull();
    }
}
