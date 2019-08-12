package by.vironit.training.basumatarau.messenger.unitTest.repositoryTests;

import by.vironit.training.basumatarau.messenger.model.User;
import by.vironit.training.basumatarau.messenger.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class UserRepositoryTest extends BaseRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindUser_thenReturnUserEmail() throws InstantiationException {
        final Optional<User> anyUser = users.stream().findAny();
        if(!anyUser.isPresent()){
            fail("no user found for testing");
        }
        final User user = anyUser.get();

        User retrieved = userRepository.findByEmail(user.getEmail())
                .orElseThrow(()-> new RuntimeException("failure to fetch any user"));
        assertThat(user.getEmail()).isEqualTo(retrieved.getEmail());
    }

    @Test
    public void whenFindUserById_thenReturnUserEmail() throws InstantiationException {
        final Optional<User> anyUser = users.stream().findAny();
        if(!anyUser.isPresent()){
            fail("no user found for testing");
        }
        final User user = anyUser.get();

        User retrieved = userRepository.findByEmail(user.getEmail())
                .orElseThrow(()-> new RuntimeException("failure to fetch any user"));
        assertThat(retrieved.getEmail()).isNotNull();
    }

}
