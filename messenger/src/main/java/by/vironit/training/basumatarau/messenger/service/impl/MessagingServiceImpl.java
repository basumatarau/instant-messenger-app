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

        privateMessageRepository.save(newPrivateMessage);

        final MessageDto outboundMessageDto = modelMapper.map(newPrivateMessage, MessageDto.class);

        simpMessagingTemplate.convertAndSendToUser(
                personalContactVo.getPerson().getEmail(),
                "/queue",
                outboundMessageDto
        );

        simpMessagingTemplate.convertAndSendToUser(
                personalContactVo.getOwner().getEmail(),
                "/queue",
                outboundMessageDto
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

        distributedMessageRepository.save(newDistributedMessage);

        for (Subscription subscription : chatRoom.getSubscriptions()) {
            simpMessagingTemplate.convertAndSendToUser(
                    subscription.getOwner().getEmail(),
                    "/queue",
                    modelMapper.map(newDistributedMessage, MessageDto.class)
            );
        }
    }
}
