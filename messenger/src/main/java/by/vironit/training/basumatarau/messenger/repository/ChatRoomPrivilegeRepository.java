package by.vironit.training.basumatarau.messenger.repository;

import by.vironit.training.basumatarau.messenger.model.ChatRoomPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomPrivilegeRepository
        extends JpaRepository<ChatRoomPrivilege, Integer> {
    ChatRoomPrivilege findByName(String name);
}
