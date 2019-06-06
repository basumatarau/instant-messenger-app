package by.vironit.training.basumatarau.instantMessengerApp.dao.impl;

import by.vironit.training.basumatarau.instantMessengerApp.dao.CrudDao;
import by.vironit.training.basumatarau.instantMessengerApp.dao.DaoProvider;
import by.vironit.training.basumatarau.instantMessengerApp.exception.DaoException;
import by.vironit.training.basumatarau.instantMessengerApp.model.Role;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDaoImpl extends BaseDao implements CrudDao<User, Long> {

    final CrudDao<User, Long> userDao = DaoProvider.DAO.userDao;

    private static final String FIND_USERS_BY_ID_SQL_STATEMENT
            = "select *, u.id as id_user, r.id as role_id, r.\"name\" as role_name from instant_messenger_db_schema.users as u " +
            "            left outer join instant_messenger_db_schema.roles as r " +
            "            on u.id_role = r.id " +
            "            where u.id=?";
    private static final String FIND_USER_BY_EMAIL_SQL_STATEMENT
            = "select * from instant_messenger_db_schema.users as u where u.email=?";
    private static final String INSERT_USER_SQL_STATEMENT
            = "insert into instant_messenger_db_schema.users " +
            "            (id, firstname, lastname, nickname, email, passwordhash, enabled, id_role) " +
            "            values(default, ?, ?, ?, ?, ?, ?, ?);";

    @Override
    public Optional<User> findById(Long aLong) throws DaoException {
        final Connection connection = getConnectionPool().takeConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            //todo salt
            ps = connection.prepareStatement(FIND_USERS_BY_ID_SQL_STATEMENT);
            ps.setLong(1, aLong);
            resultSet = ps.executeQuery();

            if(resultSet.next()){
                final Role role = new Role.RoleBuilder()
                        .name(resultSet.getString("role_name"))
                        .build();

                user = new User.UserBuilder()
                        .id(resultSet.getLong("id_user"))
                        .firstName(resultSet.getString("firstName"))
                        .lastName(resultSet.getString("lastName"))
                        .nickName(resultSet.getString("nickName"))
                        .passwordHash(resultSet.getString("passwordHash"))
                        .email(resultSet.getString("email"))
                        .role(role)
                        .enabled(true)
                        .build();
            }

            connection.commit();
        } catch (SQLException | InstantiationException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                //todo logger.log
                throw new RuntimeException("failed to rollback transaction", ex);
            }
            throw new DaoException(e);
        } finally {
            getConnectionPool()
                    .closeConnection(resultSet, ps, connection);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public boolean save(User user) throws DaoException {
        final Connection connection = getConnectionPool().takeConnection();
        PreparedStatement ps = null;

        try {

            //todo salt
            ps = connection.prepareStatement(INSERT_USER_SQL_STATEMENT);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getNickName());
            ps.setString(4, user.getNickName());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getPasswordHash());
            ps.setBoolean(7, user.getEnabled());

            connection.commit();

            final ResultSet generatedKeys = ps.getGeneratedKeys();
            if(generatedKeys.next()){
                return generatedKeys.getLong(1) > 0;
            }
        } catch (SQLException e) {

            try {
                connection.rollback();
            } catch (SQLException ex) {
                //todo logger.log
                throw new RuntimeException("failed to rollback transaction", ex);
            }
            throw new DaoException(e);
        } finally {
            getConnectionPool()
                    .closeConnection(ps, connection);
        }
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

    public Optional<User> findByEmail(String email) throws DaoException{
        final Connection connection = getConnectionPool().takeConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            //todo salt
            ps = connection.prepareStatement(FIND_USER_BY_EMAIL_SQL_STATEMENT);
            ps.setString(1, email);
            resultSet = ps.executeQuery();

            if(resultSet.next()){
                final Role role = new Role.RoleBuilder()
                        .id(resultSet.getInt("role_id"))
                        .name(resultSet.getString("role_name"))
                        .build();

                user = new User.UserBuilder()
                        .id(resultSet.getLong("id_user"))
                        .firstName(resultSet.getString("firstName"))
                        .lastName(resultSet.getString("lastName"))
                        .nickName(resultSet.getString("nickName"))
                        .passwordHash(resultSet.getString("passwordHash"))
                        .email(resultSet.getString("email"))
                        .role(role)
                        .enabled(true)
                        .build();
            }

            connection.commit();
        } catch (SQLException | InstantiationException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                //todo logger.log
                throw new RuntimeException("failed to rollback transaction", ex);
            }
            throw new DaoException(e);
        } finally {
            getConnectionPool()
                    .closeConnection(resultSet, ps, connection);
        }
        return Optional.ofNullable(user);
    }
}
