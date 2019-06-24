package by.vironit.training.basumatarau.instantMessengerApp.service.impl;

import by.vironit.training.basumatarau.instantMessengerApp.controller.SessionHandler;
import by.vironit.training.basumatarau.instantMessengerApp.dao.ContactDao;
import by.vironit.training.basumatarau.instantMessengerApp.dao.DaoProvider;
import by.vironit.training.basumatarau.instantMessengerApp.dao.UserDao;
import by.vironit.training.basumatarau.instantMessengerApp.dto.UserDto;
import by.vironit.training.basumatarau.instantMessengerApp.exception.DaoException;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ServiceException;
import by.vironit.training.basumatarau.instantMessengerApp.model.Contact;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;
import by.vironit.training.basumatarau.instantMessengerApp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDao userDao;
    private final ContactDao contactDao;
    {
        userDao = DaoProvider.DAO.userDao;
        contactDao = DaoProvider.DAO.contactDao;
    }

    @Override
    public Optional<User> findUserByEmail(String email)
            throws ServiceException {
        try {
            return userDao.findByEmail(email);
        } catch (DaoException e) {
            logger.warn("failed to find user by email: " + email);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean registerNewUserAccount(User user)
            throws ServiceException {
        try {
            return userDao.save(user);
        } catch (DaoException e) {
            logger.warn("failed to register user: " + user);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<UserDto> searchUsersWithPattern(User user, String pattern)
            throws ServiceException {
        final String trimmed = pattern.trim();
        List<User> users;
        List<Contact> contactsForUser;
        try {
            users = userDao.searchUsersWithPattern(trimmed);
        } catch (DaoException e) {
            logger.warn("failed to search for users with look up pattern: " + pattern);
            throw new ServiceException("failed to fetch users for pattern " + pattern, e);
        }
        users.remove(user);
        try {
            contactsForUser = contactDao.getContactsForUser(user);
        } catch (DaoException e) {
            logger.warn("failed to fetch contacts for user: " + user);
            throw new ServiceException("failed to fetch users for pattern " + pattern, e);
        }

        return users
                .stream()
                .map(u -> {
                    final UserDto uDto = UserDto.getDto(u);
                    final Optional<Contact> c = contactsForUser
                            .stream()
                            .filter(contact -> contact.getPerson().equals(u))
                            .findFirst();
                    if(c.isPresent()){
                        if(c.get().getIsConfirmed()){
                            uDto.setStatus(UserDto.Status.MY_FRIEND);
                        }else{
                            uDto.setStatus(UserDto.Status.PENDING);
                        }
                    }else {
                        uDto.setStatus(UserDto.Status.STRANGER);
                    }
                    return uDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findUserById(Long id)
            throws ServiceException {
        try {
            return userDao.findById(id);
        } catch (DaoException e) {
            logger.warn("failed to find user by id: " + id);
            throw new ServiceException(e);
        }
    }
}
