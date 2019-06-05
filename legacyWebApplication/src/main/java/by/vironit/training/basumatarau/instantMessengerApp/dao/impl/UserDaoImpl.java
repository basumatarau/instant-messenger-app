package by.vironit.training.basumatarau.instantMessengerApp.dao.impl;

import by.vironit.training.basumatarau.instantMessengerApp.dao.CrudDao;
import by.vironit.training.basumatarau.instantMessengerApp.dao.DaoProvider;
import by.vironit.training.basumatarau.instantMessengerApp.dao.criteria.Criteria;
import by.vironit.training.basumatarau.instantMessengerApp.exception.DaoException;
import by.vironit.training.basumatarau.instantMessengerApp.model.Role;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends BaseDao implements CrudDao<User, Long> {

    private DaoProvider daoProvider = DaoProvider.DAO;

    private static final String FIND_USERS_BY_ID_PREPARED_STATEMENT
            = "select *, r.id as role_id, r.\"name\" as role_name from instant_messenger_db_schema.users as u " +
            "left outer join instant_messenger_db_schema.roles as r " +
            "on u.id_role = r.id " +
            "where u.id=?";

    @Override
    public Optional<User> findById(Long aLong) throws DaoException {
        final Connection connection = cPool.takeConnection();
        PreparedStatement psFindUserById = null;
        User user = null;
        try {
            psFindUserById = connection.prepareStatement(FIND_USERS_BY_ID_PREPARED_STATEMENT);
            psFindUserById.setLong(1, aLong);
            //todo fix somewhere here
            final ResultSet resultSet = psFindUserById.executeQuery();
            if(resultSet.next()){
                final Role role = new Role.RoleBuilder()
                        .name(resultSet.getString("role_name"))
                        .build();

                //todo id to be set as well
                user = new User.UserBuilder()
                        .firstName(resultSet.getString("firstName"))
                        .lastName(resultSet.getString("lastName"))
                        .nickName(resultSet.getString("nickName"))
                        .passwordHash(resultSet.getString("passwordHash"))
                        .email(resultSet.getString("email"))
                        .role(role)
                        .enabled(true)
                        .build();
            }
        } catch (SQLException | InstantiationException e) {
            throw new DaoException(e);
        } finally {
            cPool.closeConnection(psFindUserById, connection);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public boolean save(User bean) throws DaoException {
        return false;
    }

    @Override
    public boolean update(User bean) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(User bean) throws DaoException {
        return false;
    }

    @Override
    public List<User> findAllWhere(String sqlWHERE) throws DaoException {
        return null;
    }

    @Override
    public <R> List<User> findAllBy(Criteria<R> criteria) throws DaoException {
        return null;
    }
}
