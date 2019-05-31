package by.vironit.training.basumatarau.instantMessengerApp.repository;

import by.vironit.training.basumatarau.instantMessengerApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
