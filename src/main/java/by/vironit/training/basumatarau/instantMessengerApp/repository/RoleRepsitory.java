package by.vironit.training.basumatarau.instantMessengerApp.repository;

import by.vironit.training.basumatarau.instantMessengerApp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepsitory extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
