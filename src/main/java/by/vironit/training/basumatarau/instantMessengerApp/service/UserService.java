package by.vironit.training.basumatarau.instantMessengerApp.service;

import by.vironit.training.basumatarau.instantMessengerApp.dto.ContactEntryVo;
import by.vironit.training.basumatarau.instantMessengerApp.dto.UserAccountRegistrationDto;
import by.vironit.training.basumatarau.instantMessengerApp.dto.UserProfileDto;

import java.util.Set;

public interface UserService {
    UserProfileDto getUserProfileDtoByUserEmail(String email);
    void registerNewUserAccount(UserAccountRegistrationDto credentials);
    Set<ContactEntryVo> getUserContactEntriesByUserEmail(String email);
}
