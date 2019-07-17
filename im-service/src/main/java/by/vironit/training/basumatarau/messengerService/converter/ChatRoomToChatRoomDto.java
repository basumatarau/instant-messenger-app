package by.vironit.training.basumatarau.messengerService.converter;

import by.vironit.training.basumatarau.messengerService.dto.ChatRoomDto;
import by.vironit.training.basumatarau.messengerService.model.ChatRoom;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class ChatRoomToChatRoomDto implements Converter<ChatRoom, ChatRoomDto> {
    @Override
    public ChatRoomDto convert(MappingContext<ChatRoom, ChatRoomDto> context) {
        return context.getSource() == null ? null :
                new ChatRoomDto(
                        context.getSource().getId(),
                        context.getSource().getName());
    }
}
