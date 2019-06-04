package by.vironit.training.basumatarau.instantMessengerApp.persistenceTests.repositoryTests;

import by.vironit.training.basumatarau.instantMessengerApp.repository.ChatRoomRepository;
import by.vironit.training.basumatarau.instantMessengerApp.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class SubscriberRepositoryTest extends BaseRepositoryTest{
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;


}
