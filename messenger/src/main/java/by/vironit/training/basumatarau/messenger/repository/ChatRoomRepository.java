package by.vironit.training.basumatarau.messenger.repository;

import by.vironit.training.basumatarau.messenger.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query(value = "select cr from ChatRoom cr " +
            "join fetch cr.subscriptions subs " +
            "where cr.id=:#{#chatRoomId} ")
    Optional<ChatRoom> findChatRoomByIdWithAllPeers(@Param("chatRoomId") Long id);
}
