package by.vironit.training.basumatarau.messengerService.service.impl;

import by.vironit.training.basumatarau.messengerService.dto.UserCredentialsDto;
import by.vironit.training.basumatarau.messengerService.dto.UserProfileDto;
import by.vironit.training.basumatarau.messengerService.repository.UserRepository;
import by.vironit.training.basumatarau.messengerService.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserProfileDto authenticate(UserCredentialsDto credentials) {
        //todo to be implemented
        return null;
    }
}
