package by.vironit.training.basumatarau.instantMessengerApp.repository;

import by.vironit.training.basumatarau.instantMessengerApp.model.ChatRoomPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomPrivilegeRepository
        extends JpaRepository<ChatRoomPrivilege, Integer> {
    ChatRoomPrivilege findByName(String name);
}
