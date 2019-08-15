package by.vironit.training.basumatarau.messenger.repository;

import by.vironit.training.basumatarau.messenger.model.ChatRoom;
import by.vironit.training.basumatarau.messenger.model.ImageResource;
import by.vironit.training.basumatarau.messenger.model.MessageResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageResourceRepository
        extends JpaRepository<MessageResource, Long> {

    @Query(value = "select img from ImageResource img " +
            "")
    List<ImageResource> findImagesForChatRoom(ChatRoom chatRoom);
}
