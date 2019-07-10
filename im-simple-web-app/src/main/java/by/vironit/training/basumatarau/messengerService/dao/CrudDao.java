package by.vironit.training.basumatarau.messengerService.dao;

import by.vironit.training.basumatarau.messengerService.exception.DaoException;

import java.io.Serializable;
import java.util.Optional;

public interface CrudDao<T, ID extends Serializable> {
    Optional<T> findById(ID id) throws DaoException;
    boolean save(T bean) throws DaoException;
    boolean update(T bean) throws DaoException;
    boolean delete(T bean) throws DaoException;
}
