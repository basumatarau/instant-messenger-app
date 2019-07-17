package by.vironit.training.basumatarau.messengerService.converter;

import by.vironit.training.basumatarau.messengerService.dto.*;
import by.vironit.training.basumatarau.messengerService.model.Subscription;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionToSubscriptionVo implements Converter<Subscription, ContactEntryVo> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SubscriptionVo convert(MappingContext<Subscription, ContactEntryVo> context) {
        return context.getSource() == null ? null :
                new SubscriptionVo(context.getSource().getId(),
                modelMapper.map(context.getSource().getOwner(), UserProfileDto.class),
                modelMapper.map(context.getSource().getChatRoom(), ChatRoomDto.class));
    }
}
