package by.vironit.training.basumatarau.instantMessengerApp.persistenceTests.repositoryTests;

import by.vironit.training.basumatarau.instantMessengerApp.model.Role;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;
import by.vironit.training.basumatarau.instantMessengerApp.repository.RoleRepsitory;
import by.vironit.training.basumatarau.instantMessengerApp.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.initialization-mode=always",
        "spring.datasource.url=jdbc:postgresql://localhost:5432/im-db-training-project",
        "spring.datasource.username=postgres",
        "spring.datasource.password=password",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect"
})
public class BaseRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepsitory roleRepsitory;

    Set<User> users = new HashSet<>();

    @Before
    public void initBase() throws InstantiationException {
        final Role userDefaultRole = roleRepsitory.findById(2).get();
        users.add(new User.UserBuilder()
                .role(userDefaultRole)
                .enabled(true)
                .firstName("TestFirstName1")
                .lastName("TestLastName1")
                .nickName("TestNickName1")
                .email("test@email.com1")
                .passwordHash("testStub1")
                .build());
        users.add(new User.UserBuilder()
                .role(userDefaultRole)
                .enabled(true)
                .firstName("TestFirstName2")
                .lastName("TestLastName2")
                .nickName("TestNickName2")
                .email("test@email.com2")
                .passwordHash("testStub2")
                .build());
        users.add(new User.UserBuilder()
                .role(userDefaultRole)
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
    }

    @After
    public void cleanBase(){
        users.clear();
    }

}

