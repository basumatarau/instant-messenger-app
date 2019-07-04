package by.vironit.training.basumatarau.instantMessengerApp.service.impl;

import by.vironit.training.basumatarau.instantMessengerApp.dto.UserCredentialsDto;
import by.vironit.training.basumatarau.instantMessengerApp.dto.UserProfileDto;
import by.vironit.training.basumatarau.instantMessengerApp.repository.UserRepository;
import by.vironit.training.basumatarau.instantMessengerApp.service.AuthenticationService;
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
