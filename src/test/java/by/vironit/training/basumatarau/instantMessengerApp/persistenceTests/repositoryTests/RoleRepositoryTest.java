package by.vironit.training.basumatarau.instantMessengerApp.persistenceTests.repositoryTests;

import by.vironit.training.basumatarau.instantMessengerApp.model.Role;
import by.vironit.training.basumatarau.instantMessengerApp.repository.RoleRepsitory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("/test.properties")
public class RoleRepositoryTest {

    @Autowired
    private RoleRepsitory roleRepsitory;

    private Set<Role> userAndAminRoles = new HashSet<>();

    @Before
    public void init() throws InstantiationException {
        userAndAminRoles.add(new Role.RoleBuilder().name("ADMIN").build());
        userAndAminRoles.add(new Role.RoleBuilder().name("USER").build());
    }

    @After
    public void cleanUp(){
        userAndAminRoles.clear();
    }

    @Test
    public void whenGetAllRoles_thenHasAdminAndUserAndNothingElse(){
        assertThat(roleRepsitory.findAll())
                .containsExactlyElementsOf(userAndAminRoles);
    }

}
