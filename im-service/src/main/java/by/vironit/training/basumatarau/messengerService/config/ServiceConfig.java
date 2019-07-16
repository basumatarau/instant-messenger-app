package by.vironit.training.basumatarau.messengerService.config;

import by.vironit.training.basumatarau.messengerService.converter.UserProfileDtoToUser;
import by.vironit.training.basumatarau.messengerService.converter.UserToUserProfileDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServiceConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(getUserToUserProfileDtoConverter());
        modelMapper.addConverter(new UserToUserProfileDto());
        modelMapper.addConverter(getUserProfileDtoToUserConverter());
        return modelMapper;
    }

    @Bean
    public UserToUserProfileDto getUserToUserProfileDtoConverter(){
        return new UserToUserProfileDto();
    }

    @Bean
    public UserProfileDtoToUser getUserProfileDtoToUserConverter() {
        return new UserProfileDtoToUser();
    }

}
