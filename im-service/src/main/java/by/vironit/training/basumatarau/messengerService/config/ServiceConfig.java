package by.vironit.training.basumatarau.messengerService.config;

import by.vironit.training.basumatarau.messengerService.converter.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServiceConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ModelMapper modelMapper() {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(getUserToUserProfileDtoConverter());
        modelMapper.addConverter(new UserToUserProfileDto());
        modelMapper.addConverter(getUserProfileDtoToUserConverter());
        modelMapper.addConverter(new ChatRoomToChatRoomDto());
        modelMapper.addConverter(getContactToPersonalContactVo());
        modelMapper.addConverter(getSubscriptionToSubscriptionVo());
        return modelMapper;
    }

    @Bean
    public ContactToPersonalContactVo getContactToPersonalContactVo(){
        return new ContactToPersonalContactVo();
    }

    @Bean
    public SubscriptionToSubscriptionVo getSubscriptionToSubscriptionVo(){
        return new SubscriptionToSubscriptionVo();
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
