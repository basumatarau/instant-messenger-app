package by.vironit.training.basumatarau.instantMessengerApp.dao;

import by.vironit.training.basumatarau.instantMessengerApp.dao.criteria.Criteria;
import by.vironit.training.basumatarau.instantMessengerApp.exception.DaoException;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CrudDao<T, ID extends Serializable> {
    Optional<T> findById(ID id) throws DaoException;
    boolean save(T bean) throws DaoException;
    boolean update(T bean) throws DaoException;
    boolean delete(T bean) throws DaoException;
    List<T> findAllWhere(String sqlWHERE) throws DaoException;
    <R> List<T> findAllBy(Criteria<R> criteria) throws DaoException;
}
