package by.vironit.training.basumatarau.instantMessengerApp.persistenceTests.repositoryTests;

import by.vironit.training.basumatarau.instantMessengerApp.model.ChatRoom;
import by.vironit.training.basumatarau.instantMessengerApp.model.ChatRoomPrivilege;
import by.vironit.training.basumatarau.instantMessengerApp.model.Role;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;
import by.vironit.training.basumatarau.instantMessengerApp.persistenceTests.repositoryTests.config.H2TestProfileJPAConfiguration;
import by.vironit.training.basumatarau.instantMessengerApp.repository.ChatRoomPrivilegeRepository;
import by.vironit.training.basumatarau.instantMessengerApp.repository.ChatRoomRepository;
import by.vironit.training.basumatarau.instantMessengerApp.repository.RoleRepsitory;
import by.vironit.training.basumatarau.instantMessengerApp.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {H2TestProfileJPAConfiguration.class})
public class BaseRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepsitory roleRepsitory;

    @Autowired
    ChatRoomPrivilegeRepository chatRoomPrivilegeRepository;

    @Autowired
    ChatRoomRepository chatRoomRepository;

    Set<User> users = new HashSet<>();

    @Before
    public void initBase() throws InstantiationException {
        roleRepsitory.saveAndFlush(new Role.RoleBuilder().name("ADMIN").build());
        roleRepsitory.saveAndFlush(new Role.RoleBuilder().name("USER").build());

        chatRoomPrivilegeRepository.saveAndFlush(new ChatRoomPrivilege.ChatRoomPrivilegeBuilder().name("CHATADMIN").build());
        chatRoomPrivilegeRepository.saveAndFlush(new ChatRoomPrivilege.ChatRoomPrivilegeBuilder().name("COMMONER").build());

        final Role userRole = roleRepsitory.findByName("USER").orElseThrow(() -> new RuntimeException("failed to fetch USER role"));

        users.add(new User.UserBuilder()
                .role(userRole)
                .enabled(true)
                .firstName("TestFirstName1")
                .lastName("TestLastName1")
                .nickName("TestNickName1")
                .email("test@email.com1")
                .passwordHash("testStub1")
                .build());
        users.add(new User.UserBuilder()
                .role(userRole)
                .enabled(true)
                .firstName("TestFirstName2")
                .lastName("TestLastName2")
                .nickName("TestNickName2")
                .email("test@email.com2")
                .passwordHash("testStub2")
                .build());
        users.add(new User.UserBuilder()
                .role(userRole)
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
                .timeCreated(new Date())
                .build();
        chatRoomRepository.saveAndFlush(chatRoom);
    }

    @After
    public void cleanBase() {
        userRepository.deleteAll();
        roleRepsitory.deleteAll();
        chatRoomPrivilegeRepository.deleteAll();
        chatRoomRepository.deleteAll();
        users.clear();
    }

}


