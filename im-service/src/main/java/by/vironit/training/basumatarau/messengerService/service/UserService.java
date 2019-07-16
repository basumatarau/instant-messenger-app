package by.vironit.training.basumatarau.messengerService.service;

import by.vironit.training.basumatarau.messengerService.dto.ContactEntryVo;
import by.vironit.training.basumatarau.messengerService.dto.UserAccountRegistrationDto;
import by.vironit.training.basumatarau.messengerService.dto.UserProfileDto;

import java.util.Set;

public interface UserService {
    UserProfileDto getUserProfileByUserEmail(String email);
    UserProfileDto getUserById(Long id);

    void registerNewUserAccount(UserAccountRegistrationDto credentials);
    Set<ContactEntryVo> getUserContactEntriesByUserEmail(String email);
}
