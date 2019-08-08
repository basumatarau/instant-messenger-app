package by.vironit.training.basumatarau.messenger.converter;

import by.vironit.training.basumatarau.messenger.dto.ContactEntryVo;
import by.vironit.training.basumatarau.messenger.dto.MessageStatusInfoDto;
import by.vironit.training.basumatarau.messenger.model.StatusInfo;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MessageStatusInfoToMessageStatusInfoDto
        implements Converter<StatusInfo, MessageStatusInfoDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MessageStatusInfoDto convert(MappingContext<StatusInfo, MessageStatusInfoDto> context) {
        final Boolean delivered = context.getSource().getDelivered();
        final Boolean read = context.getSource().getRead();
        return context.getSource() == null ? null :
                new MessageStatusInfoDto(
                        context.getSource().getMessage().getId(),
                        delivered,
                        delivered ? new Date(context.getSource().getTimeDelivered()) : null,
                        read,
                        read ? new Date(context.getSource().getTimeRead()) : null,
                        modelMapper.map(context.getSource().getContactEntry(), ContactEntryVo.class)
                );
    }
}
