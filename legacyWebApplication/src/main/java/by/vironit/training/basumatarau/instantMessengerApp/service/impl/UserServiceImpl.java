package by.vironit.training.basumatarau.instantMessengerApp.service.impl;

import by.vironit.training.basumatarau.instantMessengerApp.dao.DaoProvider;
import by.vironit.training.basumatarau.instantMessengerApp.dao.UserDao;
import by.vironit.training.basumatarau.instantMessengerApp.dto.UserDto;
import by.vironit.training.basumatarau.instantMessengerApp.exception.DaoException;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ServiceException;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;
import by.vironit.training.basumatarau.instantMessengerApp.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = DaoProvider.DAO.userDao;

    @Override
    public Optional<User> findUserByEmail(String email)
            throws ServiceException {
        try {
            return userDao.findByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean registerNewUserAccount(User user)
            throws ServiceException {
        try {
            return userDao.save(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<UserDto> searchUsersWithPattern(String pattern)
            throws ServiceException {
        final String trimmed = pattern.trim();
        List<User> users;
        try {
            users = userDao.searchUsersWithPattern(trimmed);
        } catch (DaoException e) {
            throw new ServiceException("failed to fetch users for pattern " + pattern, e);
        }
        return users
                .stream()
                .map(UserDto::getDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findUserById(Long id)
            throws ServiceException {
        try {
            return userDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
