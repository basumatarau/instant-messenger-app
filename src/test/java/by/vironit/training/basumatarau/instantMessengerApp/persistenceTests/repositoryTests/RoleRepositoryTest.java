package by.vironit.training.basumatarau.instantMessengerApp.persistenceTests.repositoryTests;

import by.vironit.training.basumatarau.instantMessengerApp.model.Role;
import by.vironit.training.basumatarau.instantMessengerApp.repository.RoleRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class RoleRepositoryTest extends BaseRepositoryTest{

    @Autowired
    private RoleRepository roleRepsitory;

    private Set<Role> userAndAdminRoles = new HashSet<>();

    @Before
    public void init() throws InstantiationException {
        userAndAdminRoles.add(new Role.RoleBuilder().name("ADMIN").build());
        userAndAdminRoles.add(new Role.RoleBuilder().name("USER").build());
    }

    @After
    public void cleanUp(){
        userAndAdminRoles.clear();
    }

    @Test
    public void whenGetAllRoles_thenHasAdminAndUserAndNothingElse(){
        assertThat(roleRepsitory.findAll())
                .containsExactlyElementsOf(userAndAdminRoles);
    }

}
