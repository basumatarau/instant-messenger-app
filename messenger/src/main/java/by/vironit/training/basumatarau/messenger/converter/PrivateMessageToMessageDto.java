package by.vironit.training.basumatarau.messenger.converter;

import by.vironit.training.basumatarau.messenger.dto.ContactEntryVo;
import by.vironit.training.basumatarau.messenger.dto.MessageDto;
import by.vironit.training.basumatarau.messenger.dto.MessageStatusInfoDto;
import by.vironit.training.basumatarau.messenger.dto.UserProfileDto;
import by.vironit.training.basumatarau.messenger.model.PrivateMessage;
import by.vironit.training.basumatarau.messenger.model.StatusInfo;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PrivateMessageToMessageDto
        implements Converter<PrivateMessage, MessageDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MessageDto convert(MappingContext<PrivateMessage, MessageDto> context) {
        return context.getSource() == null ? null :
                new MessageDto(
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
