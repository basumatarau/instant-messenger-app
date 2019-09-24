package by.vironit.training.basumatarau.messenger.converter;

import by.vironit.training.basumatarau.messenger.dto.UserAccountRegistrationDto;
import by.vironit.training.basumatarau.messenger.model.User;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserAccountDtoToUser
        implements Converter<UserAccountRegistrationDto, User> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User convert(MappingContext<UserAccountRegistrationDto, User> context) {
        return context.getSource() == null ? null :
                new User.UserBuilder()
                        .email(context.getSource().getEmail())
                        .enabled(true)
                        .authProvider(User.AuthProvider.local)
                        .firstName(context.getSource().getFirstName())
                        .lastName(context.getSource().getLastName())
                        .nickName(context.getSource().getNickName())
                        .passwordHash(passwordEncoder.encode(context.getSource().getRawPassword()))
                        .role(User.UserRole.USER)
                        .build();
    }
}
