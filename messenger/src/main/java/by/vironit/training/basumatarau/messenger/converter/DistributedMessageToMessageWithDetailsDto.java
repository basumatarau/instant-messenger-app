package by.vironit.training.basumatarau.messenger.converter;

import by.vironit.training.basumatarau.messenger.dto.*;
import by.vironit.training.basumatarau.messenger.model.ChatRoom;
import by.vironit.training.basumatarau.messenger.model.DistributedMessage;
import by.vironit.training.basumatarau.messenger.model.Subscription;
import by.vironit.training.basumatarau.messenger.model.User;
import by.vironit.training.basumatarau.messenger.repository.SubscriptionRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.persistence.EntityNotFoundException;
import java.util.Date;

@Component
public class DistributedMessageToMessageWithDetailsDto
        implements Converter<DistributedMessage, MessageWithDetailsDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public MessageWithDetailsDto convert(
            MappingContext<DistributedMessage,
            MessageWithDetailsDto> context) {

        final User author = context.getSource().getAuthor();
        final ChatRoom chatRoom = context.getSource().getChatRoom();

        final Subscription subscription =
                subscriptionRepository.findSubscriptionByChatRoomAndOwner(chatRoom, author)
                .orElseThrow(() -> new EntityNotFoundException("no sub found"));

        return context.getSource() == null ? null :
                new MessageWithDetailsDto(
                        context.getSource().getId(),
                        modelMapper.map(author, UserProfileDto.class),
                        context.getSource().getBody(),
                        new Date(context.getSource().getTimeSent()),
                        modelMapper.map(subscription, ContactEntryVo.class),
                        context.getSource()
                                .getDeliveries()
                                .stream()
                                .map(statusInfo -> modelMapper.map(statusInfo, MessageStatusInfoDto.class))
                                .toArray(MessageStatusInfoDto[]::new)
                );
    }
}
