package by.vironit.training.basumatarau.instantMessengerApp.dao.impl;

import by.vironit.training.basumatarau.instantMessengerApp.dao.UserDao;
import by.vironit.training.basumatarau.instantMessengerApp.exception.DaoException;
import by.vironit.training.basumatarau.instantMessengerApp.model.Role;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends BaseDao implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    private static final String SEARCH_USERS_WITH_PATTERN
            = "select " +
            "r.id as role_id, " +
            "r.\"name\" as role_name, " +
            "u.email as user_email, " +
            "u.id as user_id, " +
            "u.firstname as user_fname, " +
            "u.lastname as user_lname, " +
            "u.nickname as user_nname, " +
            "u.enabled as user_enabled, " +
            "u.passwordhash as user_pwd_hash, " +
            "u.enabled as user_enabled, " +
            "u.salt as user_salt " +
            "from legacy_im_db_schema.users as u join legacy_im_db_schema.roles as r " +
            "on u.id_role = r.id " +
            "where " +
            "u.email like ? or " +
            "u.nickname like ? escape '!' ";

    private static final String FIND_USER_BY_ID_SQL_STATEMENT
            = "select " +
            "u.id as user_id, " +
            "firstname, " +
            "lastname, " +
            "nickname, " +
            "email, " +
            "salt, " +
            "passwordhash, " +
            "enabled, " +
            "id_role, " +
            "r.\"name\" as role_name " +
            "from legacy_im_db_schema.users as u " +
            "join legacy_im_db_schema.roles as r " +
            "on u.id_role=r.id " +
            "where u.id=?";
    private static final String FIND_USER_BY_EMAIL_SQL_STATEMENT
            = "select " +
            "u.id as user_id, " +
            "firstname, " +
            "lastname, " +
            "nickname, " +
            "email, " +
            "salt, " +
            "passwordhash, " +
            "enabled, " +
            "id_role, " +
            "r.\"name\" as role_name " +
            "from legacy_im_db_schema.users as u " +
            "join legacy_im_db_schema.roles as r " +
            "on u.id_role=r.id " +
            "where u.email=?";
    private static final String INSERT_USER_SQL_STATEMENT
            = "INSERT INTO legacy_im_db_schema.users " +
            "(firstname, lastname, nickname, email, salt, passwordhash, enabled, id_role) " +
            "VALUES(?, ?, ?, ?, decode(?, 'hex'), ?, ?, ?); ";
    private static final String INSERT_USER_SQL_AND_GEN_KEY_STATEMENT
            = "INSERT INTO legacy_im_db_schema.users as u " +
            "(firstname, lastname, nickname, email, salt, passwordhash, enabled, id_role) " +
            "VALUES(?, ?, ?, ?, decode(?, 'hex'), ?, ?, ?) " +
            "RETURNING u.id ";

    @Override
    public Optional<User> findById(Long aLong) throws DaoException {
        final Connection connection = getConnectionPool().takeConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            ps = connection.prepareStatement(FIND_USER_BY_ID_SQL_STATEMENT);
            ps.setLong(1, aLong);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                final Role role = new Role.RoleBuilder()
                        .id(resultSet.getInt("id_role"))
                        .name(resultSet.getString("role_name"))
                        .build();

                user = new User.UserBuilder()
                        .id(resultSet.getLong("user_id"))
                        .firstName(resultSet.getString("firstName"))
                        .lastName(resultSet.getString("lastName"))
                        .nickName(resultSet.getString("nickName"))
                        .passwordHash(resultSet.getString("passwordHash"))
                        .email(resultSet.getString("email"))
                        .salt(resultSet.getBytes("salt"))
                        .role(role)
                        .enabled(true)
                        .build();
            }
        } catch (SQLException | InstantiationException e) {
            logger.error("failed to find user by id: " + aLong);
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
            ps = connection.prepareStatement(INSERT_USER_SQL_AND_GEN_KEY_STATEMENT,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getNickName());
            ps.setString(4, user.getEmail());
            ps.setString(5, Hex.encodeHexString(user.getSalt()));
            ps.setString(6, user.getPasswordHash());
            ps.setBoolean(7, user.getEnabled());
            ps.setInt(8, user.getRole().getId());

            ps.executeUpdate();
            connection.commit();

            final ResultSet set = ps.getGeneratedKeys();
            set.next();

            return set.getInt(1) > 0;

        } catch (SQLException e) {

            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("failed to persist user: " + user);
                throw new RuntimeException("failed to rollback transaction", ex);
            }
            throw new DaoException(e);
        } finally {
            getConnectionPool()
                    .closeConnection(ps, connection);
        }
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
    public Optional<User> findByEmail(String email) throws DaoException {
        final Connection connection = getConnectionPool().takeConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            ps = connection.prepareStatement(FIND_USER_BY_EMAIL_SQL_STATEMENT);
            ps.setString(1, email);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                final Role role = new Role.RoleBuilder()
                        .id(resultSet.getInt("id_role"))
                        .name(resultSet.getString("role_name"))
                        .build();

                user = new User.UserBuilder()
                        .id(resultSet.getLong("user_id"))
                        .firstName(resultSet.getString("firstName"))
                        .lastName(resultSet.getString("lastName"))
                        .nickName(resultSet.getString("nickName"))
                        .passwordHash(resultSet.getString("passwordHash"))
                        .email(resultSet.getString("email"))
                        .salt(resultSet.getBytes("salt"))
                        .role(role)
                        .enabled(true)
                        .build();
            }

            connection.commit();
        } catch (SQLException | InstantiationException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("failed to fetch user by email: " + email);
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
    public List<User> searchUsersWithPattern(String pattern) throws DaoException {

        final Connection connection = getConnectionPool().takeConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<User> users = new LinkedList<>();
        pattern = String.format("%%%s%%", pattern
                .replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_")
                .replace("[", "!["));

        try {
            ps = connection.prepareStatement(SEARCH_USERS_WITH_PATTERN);
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                final Role role = new Role.RoleBuilder()
                        .id(resultSet.getInt("role_id"))
                        .name(resultSet.getString("role_name"))
                        .build();

                final User user = new User.UserBuilder()
                        .id(resultSet.getLong("user_id"))
                        .firstName(resultSet.getString("user_fName"))
                        .lastName(resultSet.getString("user_lName"))
                        .nickName(resultSet.getString("user_nName"))
                        .passwordHash(resultSet.getString("user_pwd_hash"))
                        .email(resultSet.getString("user_email"))
                        .salt(resultSet.getBytes("user_salt"))
                        .role(role)
                        .enabled(resultSet.getBoolean("user_enabled"))
                        .build();

                users.add(user);
            }
        } catch (SQLException | InstantiationException e) {
            logger.error("failed to look up a user by pattern: " + pattern);
            throw new DaoException(e);
        } finally {
            getConnectionPool()
                    .closeConnection(resultSet, ps, connection);
        }
        return users;
    }
}
