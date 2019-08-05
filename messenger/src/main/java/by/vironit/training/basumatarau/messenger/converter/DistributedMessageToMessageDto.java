package by.vironit.training.basumatarau.messenger.converter;

import by.vironit.training.basumatarau.messenger.dto.ContactEntryVo;
import by.vironit.training.basumatarau.messenger.dto.MessageDto;
import by.vironit.training.basumatarau.messenger.dto.SubscriptionVo;
import by.vironit.training.basumatarau.messenger.dto.UserProfileDto;
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
public class DistributedMessageToMessageDto
        implements Converter<DistributedMessage, MessageDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public MessageDto convert(MappingContext<DistributedMessage, MessageDto> context) {
        final User author = context.getSource().getAuthor();
        final ChatRoom chatRoom = context.getSource().getChatRoom();

        final Subscription subscription =
                subscriptionRepository.findSubscriptionByChatRoomAndOwner(chatRoom, author)
                .orElseThrow(() -> new EntityNotFoundException("no sub found"));

        return context.getSource() == null ? null :
                new MessageDto(
                        context.getSource().getId(),
                        modelMapper.map(author, UserProfileDto.class),
                        context.getSource().getBody(),
                        new Date(context.getSource().getTimeSent()),
                        modelMapper.map(subscription, ContactEntryVo.class));
    }
}
