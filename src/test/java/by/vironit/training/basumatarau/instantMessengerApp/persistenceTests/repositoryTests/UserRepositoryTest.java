package by.vironit.training.basumatarau.instantMessengerApp.persistenceTests.repositoryTests;

import by.vironit.training.basumatarau.instantMessengerApp.model.User;
import by.vironit.training.basumatarau.instantMessengerApp.repository.RoleRepsitory;
import by.vironit.training.basumatarau.instantMessengerApp.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=validate",
        "spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect",
        "spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true",
        "spring.jpa.show-sql=true",
        "spring.datasource.initialization-mode=always",
        "spring.datasource.url=jdbc:postgresql://localhost:5432/im-db-training-project",
        "spring.datasource.username=postgres",
        "spring.datasource.password=password"
})
public class UserRepositoryTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepsitory roleRepsitory;

    @Test
    public void whenFindUser_thenReturnUserEmail() throws InstantiationException {
        User user = new User.UserBuilder()
                .role(roleRepsitory.findById(1).get())
                .enabled(true)
                .firstName("TestFirstName")
                .lastName("TestLastName")
                .nickName("TestNickName")
                .email("test@email.com")
                .passwordHash("testStub")
                .build();
        userRepository.saveAndFlush(user);

        User retrieved = userRepository.findByEmail(user.getEmail());

        assertThat(user.getEmail()).isEqualTo(retrieved.getEmail());
    }

    @Test
    public void whenFindUserById_thenReturnUserEmail() throws InstantiationException {
        User retrieved = userRepository.findById(1L).get();
        assertThat(retrieved.getEmail()).isNotNull();
    }

}
