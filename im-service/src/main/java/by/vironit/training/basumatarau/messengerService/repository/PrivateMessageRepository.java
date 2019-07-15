package by.vironit.training.basumatarau.messengerService.repository;

import by.vironit.training.basumatarau.messengerService.model.Contact;
import by.vironit.training.basumatarau.messengerService.model.Message;
import by.vironit.training.basumatarau.messengerService.model.PrivateMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrivateMessageRepository
        extends JpaRepository<PrivateMessage, Long> {

    List<Message> findByContact(Contact contact);

    Slice<Message> findByContact(Contact contact, Pageable pageable);
}
