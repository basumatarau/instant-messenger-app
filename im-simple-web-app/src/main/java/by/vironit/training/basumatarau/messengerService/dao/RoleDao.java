package by.vironit.training.basumatarau.messengerService.dao;

import by.vironit.training.basumatarau.messengerService.exception.DaoException;
import by.vironit.training.basumatarau.messengerService.model.Role;

import java.util.Optional;

public interface RoleDao extends CrudDao<Role, Integer> {
    Optional<Role> findByName(String name) throws DaoException;
}
