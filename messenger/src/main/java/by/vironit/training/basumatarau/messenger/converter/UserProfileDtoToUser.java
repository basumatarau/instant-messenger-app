package by.vironit.training.basumatarau.messenger.converter;

import by.vironit.training.basumatarau.messenger.dto.UserProfileDto;
import by.vironit.training.basumatarau.messenger.exception.NoEntityFound;
import by.vironit.training.basumatarau.messenger.model.User;
import by.vironit.training.basumatarau.messenger.repository.UserRepository;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserProfileDtoToUser implements Converter<UserProfileDto, User> {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User convert(MappingContext<UserProfileDto, User> context) {
        return context.getSource() == null ? null :
                userRepository.findByEmail(context.getSource().getEmail())
                        .orElseThrow(()-> new NoEntityFound("user not found"));
    }
}
