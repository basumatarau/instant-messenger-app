package by.vironit.training.basumatarau.messengerService.service;

import by.vironit.training.basumatarau.messengerService.dto.UserCredentialsDto;
import by.vironit.training.basumatarau.messengerService.dto.UserProfileDto;

public interface AuthenticationService {
    UserProfileDto authenticate(UserCredentialsDto credentials);
}
