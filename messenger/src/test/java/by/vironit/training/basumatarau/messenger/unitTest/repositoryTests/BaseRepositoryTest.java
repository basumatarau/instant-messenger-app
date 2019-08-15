package by.vironit.training.basumatarau.messenger.unitTest.repositoryTests;

import by.vironit.training.basumatarau.messenger.model.ChatRoom;
import by.vironit.training.basumatarau.messenger.model.User;
import by.vironit.training.basumatarau.messenger.unitTest.repositoryTests.config.H2TestProfileJPAConfiguration;
import by.vironit.training.basumatarau.messenger.repository.ChatRoomRepository;
import by.vironit.training.basumatarau.messenger.repository.UserRepository;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@ActiveProfiles("dao-test")
@ContextConfiguration(classes = {H2TestProfileJPAConfiguration.class})
public abstract class BaseRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChatRoomRepository chatRoomRepository;

    Set<User> users = new HashSet<>();

    @Before
    public void initBase() throws InstantiationException {

        users.add(new User.UserBuilder()
                .role(User.UserRole.USER)
                .enabled(true)
                .firstName("TestFirstName1")
                .lastName("TestLastName1")
                .nickName("TestNickName1")
                .email("test@email.com1")
                .passwordHash("testStub1")
                .build());
        users.add(new User.UserBuilder()
                .role(User.UserRole.USER)
                .enabled(true)
                .firstName("TestFirstName2")
                .lastName("TestLastName2")
                .nickName("TestNickName2")
                .email("test@email.com2")
                .passwordHash("testStub2")
                .build());
        users.add(new User.UserBuilder()
                .role(User.UserRole.USER)
                .enabled(true)
                .firstName("TestFirstName3")
                .lastName("TestLastName3")
                .nickName("TestNickName3")
                .email("test@email.com3")
                .passwordHash("testStub3")
                .build());
        users.forEach(
                user -> userRepository.saveAndFlush(user)
        );

        final ChatRoom chatRoom
                = new ChatRoom.ChatRoomBuilder()
                .isPublic(true)
                .name("testChatRoomName")
                .timeCreated(new Date().getTime())
                .build();
        chatRoomRepository.saveAndFlush(chatRoom);
    }

    @After
    public void cleanBase() {
        chatRoomRepository.deleteAll();
        users.clear();
        userRepository.deleteAll();
    }

}


