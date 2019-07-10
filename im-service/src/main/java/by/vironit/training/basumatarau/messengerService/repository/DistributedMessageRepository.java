package by.vironit.training.basumatarau.messengerService.repository;

import by.vironit.training.basumatarau.messengerService.model.ChatRoom;
import by.vironit.training.basumatarau.messengerService.model.DistributedMessage;
import by.vironit.training.basumatarau.messengerService.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistributedMessageRepository
        extends JpaRepository<DistributedMessage, Long> {
    List<Message> findByChatRoom(ChatRoom chatRoom);
}
