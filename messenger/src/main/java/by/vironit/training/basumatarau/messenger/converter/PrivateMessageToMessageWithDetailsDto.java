package by.vironit.training.basumatarau.messenger.converter;

import by.vironit.training.basumatarau.messenger.dto.*;
import by.vironit.training.basumatarau.messenger.model.PrivateMessage;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class PrivateMessageToMessageWithDetailsDto
        implements Converter<PrivateMessage, MessageWithDetailsDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MessageWithDetailsDto convert(MappingContext<PrivateMessage, MessageWithDetailsDto> context) {
        return context.getSource() == null ? null :
                new MessageWithDetailsDto(
                        context.getSource().getId(),
                        modelMapper.map(context.getSource().getAuthor(), UserProfileDto.class),
                        context.getSource().getBody(),
                        new Date(context.getSource().getTimeSent()),
                        modelMapper.map(context.getSource().getPersonalContact(), ContactEntryVo.class),
                        new MessageStatusInfoDto[]{
                                modelMapper.map(context.getSource().getDelivery(), MessageStatusInfoDto.class)
                        }
                );
    }
}
