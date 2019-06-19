package by.vironit.training.basumatarau.instantMessengerApp.dao;

import by.vironit.training.basumatarau.instantMessengerApp.exception.DaoException;
import by.vironit.training.basumatarau.instantMessengerApp.model.Role;

import java.util.Optional;

public interface RoleDao extends CrudDao<Role, Integer> {
    Optional<Role> findByName(String name) throws DaoException;
}
