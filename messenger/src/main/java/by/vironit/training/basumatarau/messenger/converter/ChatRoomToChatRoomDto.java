package by.vironit.training.basumatarau.messenger.converter;

import by.vironit.training.basumatarau.messenger.dto.ChatRoomDto;
import by.vironit.training.basumatarau.messenger.model.ChatRoom;
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
