package by.vironit.training.basumatarau.instantMessengerApp.service;

import by.vironit.training.basumatarau.instantMessengerApp.dto.UserCredentialsDto;
import by.vironit.training.basumatarau.instantMessengerApp.dto.UserProfileDto;

public interface AuthenticationService {
    UserProfileDto authenticate(UserCredentialsDto credentials);
}
