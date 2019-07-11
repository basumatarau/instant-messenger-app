package by.vironit.training.basumatarau.simpleMessengerApp.service;

import by.vironit.training.basumatarau.simpleMessengerApp.dto.UserDto;
import by.vironit.training.basumatarau.simpleMessengerApp.exception.ServiceException;
import by.vironit.training.basumatarau.simpleMessengerApp.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findUserByEmail(String email) throws ServiceException;
    boolean registerNewUserAccount(User user) throws ServiceException;
    List<UserDto> searchUsersWithPattern(User user, String pattern) throws ServiceException;
    Optional<User> findUserById(Long id) throws ServiceException;

    void updateUser(User toBeUpdated) throws ServiceException;
}
