package by.vironit.training.basumatarau.messenger.service.impl;

import by.vironit.training.basumatarau.messenger.dto.*;
import by.vironit.training.basumatarau.messenger.model.*;
import by.vironit.training.basumatarau.messenger.repository.ChatRoomRepository;
import by.vironit.training.basumatarau.messenger.repository.DistributedMessageRepository;
import by.vironit.training.basumatarau.messenger.repository.PrivateMessageRepository;
import by.vironit.training.basumatarau.messenger.service.MessagingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;

@Service
@Transactional
public class MessagingServiceImpl implements MessagingService {

    @Autowired
    private DistributedMessageRepository distributedMessageRepository;

    @Autowired
    private PrivateMessageRepository privateMessageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void sendPrivateMessage(
            IncomingMessageDto messageDto,
            PersonalContactVo personalContactVo)
            throws InstantiationException {

        final PrivateMessage newPrivateMessage =
                new PrivateMessage.PrivateMessageBuilder()
                        .author(modelMapper.map(personalContactVo.getOwner(), User.class))
                        .contact(modelMapper.map(personalContactVo, PersonalContact.class))
                        .body(messageDto.getBody())
                        .timeSent(new Date().getTime())
                        .build();

        if(messageDto.getResources() != null) {
            for (MessageResourceDto resource : messageDto.getResources()) {
                newPrivateMessage.getResources().add(modelMapper.map(resource, MessageResource.class));
            }
        }

        privateMessageRepository.save(newPrivateMessage);

        simpMessagingTemplate.convertAndSendToUser(
                personalContactVo.getOwner().getEmail(),
                "/queue",
                modelMapper.map(newPrivateMessage, MessageWithDetailsDto.class)
        );

        simpMessagingTemplate.convertAndSendToUser(
                personalContactVo.getPerson().getEmail(),
                "/queue",
                modelMapper.map(newPrivateMessage, MessageDto.class)
        );
    }

    @Override
    public void sendDistributedMessage(
            IncomingMessageDto messageDto,
            SubscriptionVo subscriptionVo)
            throws InstantiationException {

        final ChatRoom chatRoom =
                chatRoomRepository.findChatRoomByIdWithAllPeers(subscriptionVo.getChatRoom().getId())
                .orElseThrow(() -> new EntityNotFoundException("no chatRoom found"));

        final DistributedMessage newDistributedMessage =
                new DistributedMessage.DistributedMessageBuilder()
                        .author(modelMapper.map(subscriptionVo.getOwner(), User.class))
                        .chatRoom(chatRoom)
                        .body(messageDto.getBody())
                        .timeSent(new Date().getTime())
                        .build();

        if(messageDto.getResources() != null) {
            for (MessageResourceDto resource : messageDto.getResources()) {
                newDistributedMessage
                        .getResources()
                        .add(modelMapper.map(resource, MessageResource.class));
            }
        }

        distributedMessageRepository.save(newDistributedMessage);

        for (Subscription subscription : chatRoom.getSubscriptions()) {
            simpMessagingTemplate.convertAndSendToUser(
                    subscription.getOwner().getEmail(),
                    "/queue",
                    modelMapper.map(
                            newDistributedMessage,
                            subscription.getId().equals(subscriptionVo.getId()) ?
                                    MessageWithDetailsDto.class : MessageDto.class
                    )
            );
        }
    }
}
