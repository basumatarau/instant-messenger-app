package by.vironit.training.basumatarau.instantMessengerApp.persistenceTests.repositoryTests;

import by.vironit.training.basumatarau.instantMessengerApp.model.User;
import by.vironit.training.basumatarau.instantMessengerApp.repository.RoleRepsitory;
import by.vironit.training.basumatarau.instantMessengerApp.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest extends BaseRepositoryTest {
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
