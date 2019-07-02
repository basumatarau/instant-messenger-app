package by.vironit.training.basumatarau.instantMessengerApp.persistenceTests.repositoryTests;

import by.vironit.training.basumatarau.instantMessengerApp.repository.ChatRoomRepository;
import by.vironit.training.basumatarau.instantMessengerApp.repository.SubscriberRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.fail;

public class SubscriberRepositoryTest extends BaseRepositoryTest{
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Test
    public void toBeImplemented(){
        fail("to be implemented");
    }
}
