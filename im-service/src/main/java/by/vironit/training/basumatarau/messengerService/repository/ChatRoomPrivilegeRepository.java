package by.vironit.training.basumatarau.messengerService.repository;

import by.vironit.training.basumatarau.messengerService.model.ChatRoomPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomPrivilegeRepository
        extends JpaRepository<ChatRoomPrivilege, Integer> {
    ChatRoomPrivilege findByName(String name);
}
