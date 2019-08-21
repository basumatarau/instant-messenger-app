package by.vironit.training.basumatarau.messenger.service;

import by.vironit.training.basumatarau.messenger.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface UserService {
    UserProfileDto getUserProfileByUserEmail(String email);
    UserProfileDto getUserById(Long id);

    void registerNewUserAccount(UserAccountRegistrationDto credentials);
    Set<ContactEntryVo> getUserContactEntriesByUserEmail(String email);
    void createNewChatRoom(UserProfileDto userProfile, NewChatRoomDto chatRoomDto);
    Page<UserProfileDto> searchForUsers(Pageable pageable, SearchCriteriaDto criteriaDto);
}
