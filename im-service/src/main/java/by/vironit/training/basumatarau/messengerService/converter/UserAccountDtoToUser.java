package by.vironit.training.basumatarau.messengerService.converter;

import by.vironit.training.basumatarau.messengerService.dto.UserAccountRegistrationDto;
import by.vironit.training.basumatarau.messengerService.model.User;
import by.vironit.training.basumatarau.messengerService.repository.RoleRepository;
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

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public User convert(MappingContext<UserAccountRegistrationDto, User> context) {
        return context.getSource() == null ? null :
                new User.UserBuilder()
                        .email(context.getSource().getEmail())
                        .enabled(true)
                        .firstName(context.getSource().getFirstName())
                        .lastName(context.getSource().getLastName())
                        .nickName(context.getSource().getNickName())
                        .passwordHash(passwordEncoder.encode(context.getSource().getRawPassword()))
                        .role(roleRepository.getDefaultUserRole())
                        .build();
    }
}
