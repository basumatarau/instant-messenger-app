package by.vironit.training.basumatarau.messenger.config;

import by.vironit.training.basumatarau.messenger.converter.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ModelMapperConfiguration {
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
        modelMapper.addConverter(getUserAccountDtoToUser());
        modelMapper.addConverter(getPersonalContactVoToContactConverter());
        modelMapper.addConverter(getSubscriptionVoToSubscriptionConverter());
        modelMapper.addConverter(getDistributedMessageToMessageDtoConverter());
        modelMapper.addConverter(getPrivateMessageToMessageDtoConverter());
        modelMapper.addConverter(messageStatusInfoToMessageStatusInfoDto());
        return modelMapper;
    }

    @Bean
    public UserAccountDtoToUser getUserAccountDtoToUser(){
        return new UserAccountDtoToUser();
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

    @Bean
    public PersonalContactVoToContact getPersonalContactVoToContactConverter(){
        return new PersonalContactVoToContact();
    }

    @Bean
    public SubscriptionVoToSubscription getSubscriptionVoToSubscriptionConverter(){
        return new SubscriptionVoToSubscription();
    }

    @Bean
    public DistributedMessageToMessageDto getDistributedMessageToMessageDtoConverter(){
        return new DistributedMessageToMessageDto();
    }

    @Bean
    public PrivateMessageToMessageDto getPrivateMessageToMessageDtoConverter(){
        return new PrivateMessageToMessageDto();
    }

    @Bean
    public MessageStatusInfoToMessageStatusInfoDto messageStatusInfoToMessageStatusInfoDto(){
        return new MessageStatusInfoToMessageStatusInfoDto();
    }
}
