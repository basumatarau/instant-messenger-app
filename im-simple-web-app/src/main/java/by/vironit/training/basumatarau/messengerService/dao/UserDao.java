package by.vironit.training.basumatarau.messengerService.dao;

import by.vironit.training.basumatarau.messengerService.exception.DaoException;
import by.vironit.training.basumatarau.messengerService.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends CrudDao<User, Long> {
    Optional<User> findByEmail(String email) throws DaoException;
    List<User> searchUsersWithPattern(String pattern) throws DaoException;
}
