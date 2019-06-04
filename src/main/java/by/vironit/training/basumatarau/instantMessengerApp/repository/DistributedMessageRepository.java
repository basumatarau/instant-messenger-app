package by.vironit.training.basumatarau.instantMessengerApp.repository;

import by.vironit.training.basumatarau.instantMessengerApp.model.ChatRoom;
import by.vironit.training.basumatarau.instantMessengerApp.model.DistributedMessage;
import by.vironit.training.basumatarau.instantMessengerApp.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistributedMessageRepository
        extends JpaRepository<DistributedMessage, Long> {
    List<Message> findByChatRoom(ChatRoom chatRoom);
}
