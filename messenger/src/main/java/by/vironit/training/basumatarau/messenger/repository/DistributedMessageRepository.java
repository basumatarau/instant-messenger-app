package by.vironit.training.basumatarau.messenger.repository;

import by.vironit.training.basumatarau.messenger.model.ChatRoom;
import by.vironit.training.basumatarau.messenger.model.DistributedMessage;
import by.vironit.training.basumatarau.messenger.model.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistributedMessageRepository
        extends JpaRepository<DistributedMessage, Long> {

    List<Message> findByChatRoom(ChatRoom chatRoom);

    Slice<Message> findByChatRoom(ChatRoom chatRoom, Pageable pageable);
}
