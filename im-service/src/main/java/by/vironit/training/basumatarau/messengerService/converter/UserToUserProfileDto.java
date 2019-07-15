package by.vironit.training.basumatarau.messengerService.converter;

import by.vironit.training.basumatarau.messengerService.dto.UserProfileDto;
import by.vironit.training.basumatarau.messengerService.model.User;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class UserToUserProfileDto implements Converter<User, UserProfileDto> {
    @Override
    public UserProfileDto convert(MappingContext<User, UserProfileDto> context) {
        return context.getSource() == null ? null :
                new UserProfileDto(context.getSource().getId(),
                        context.getSource().getFirstName(),
                        context.getSource().getLastName(),
                        context.getSource().getNickName(),
                        context.getSource().getEmail());
    }
}
