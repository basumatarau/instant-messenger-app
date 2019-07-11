package by.vironit.training.basumatarau.simpleMessengerApp.dao;

import by.vironit.training.basumatarau.simpleMessengerApp.exception.DaoException;
import by.vironit.training.basumatarau.simpleMessengerApp.model.Role;

import java.util.Optional;

public interface RoleDao extends CrudDao<Role, Integer> {
    Optional<Role> findByName(String name) throws DaoException;
}
