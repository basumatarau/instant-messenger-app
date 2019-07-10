package by.vironit.training.basumatarau.instantMessengerApp.repository;

import by.vironit.training.basumatarau.instantMessengerApp.model.Contact;
import by.vironit.training.basumatarau.instantMessengerApp.model.Message;
import by.vironit.training.basumatarau.instantMessengerApp.model.PrivateMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Long> {
    List<Message> findByContact(Contact contact);
}
