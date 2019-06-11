package by.vironit.training.basumatarau.instantMessengerApp.dao.impl;

import by.vironit.training.basumatarau.instantMessengerApp.dao.ContactDao;
import by.vironit.training.basumatarau.instantMessengerApp.exception.DaoException;
import by.vironit.training.basumatarau.instantMessengerApp.model.Contact;
import by.vironit.training.basumatarau.instantMessengerApp.model.Role;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ContactDaoImpl extends BaseDao implements ContactDao {

    private static final String SELECT_CONTACT_BY_OWNER_AND_PERSON =
            "select " +
                    "   c.id as id_contact, " +
                    "   c.id_owner as id_owner, " +
                    "   c.id_person as id_person, " +
                    "   c.confirmed as confirmed, " +
                    "   own.id_role as owner_id_role, " +
                    "   per.id_role as pwerson_id_role, " +
                    "   own.enabled as owner_enabled, " +
                    "   per.enabled as person_enabled, " +
                    "   own.email as owner_email, " +
                    "   per.email as person_email, " +
                    "   own.firstname as owner_firstname, " +
                    "   per.firstname as person_firstname, " +
                    "   own.lastname as owner_lastname, " +
                    "   per.lastname as person_lastname, " +
                    "   own.nickname as owner_nickname, " +
                    "   per.nickname as person_nickname, " +
                    "   own.passwordhash as owner_pwd_hash, " +
                    "   per.passwordhash as person_pwd_hash, " +
                    "   own.salt as owner_salt, " +
                    "   per.salt as person_salt " +
                    "from legacy_im_db_schema.contacts as c " +
                    "   inner join legacy_im_db_schema.users as own on " +
                    "       c.id_owner=own.id " +
                    "   inner join legacy_im_db_schema.users as per on " +
                    "       c.id_person=per.id " +
                    "where id_owner=? and id_person=? ";

    private static final String DELETE_CONTACT =
            "delete from legacy_im_db_schema.contacts as c " +
                    "where c.id=? ";

    private static final String UPDATE_CONTACT =
            "update legacy_im_db_schema.contacts as c " +
                    "set id_owner=?, id_person=?, confirmed=? " +
                    "where c.id=? ";

    private static final String SAVE_CONTACT =
            "INSERT INTO legacy_im_db_schema.contacts " +
                    "(id_owner, id_person, confirmed) " +
                    "VALUES(?, ?, ?);";

    private static final String SELECT_CONTACT_BY_ID =
            "select " +
                        "c.id as id_contact, " +
                        "c.confirmed as confirmed, " +
                        "own.id as id_owner, " +
                        "per.id as id_person, " +
                        "own.id_role as owner_id_role, " +
                        "per.id_role as pwerson_id_role, " +
                        "own.enabled as owner_enabled, " +
                        "per.enabled as person_enabled, " +
                        "own.email as owner_email, " +
                        "per.email as person_email, " +
                        "own.firstname as owner_firstname, " +
                        "per.firstname as person_firstname, " +
                        "own.lastname as owner_lastname, " +
                        "per.lastname as person_lastname, " +
                        "own.nickname as owner_nickname, " +
                        "per.nickname as person_nickname, " +
                        "own.passwordhash as owner_pwd_hash, " +
                        "per.passwordhash as person_pwd_hash, " +
                        "own.salt as owner_salt, " +
                        "per.salt as person_salt, " +
                        "own_r.\"name\" as owner_role_name, " +
                        "per_r.\"name\" as person_role_name " +
                    "from legacy_im_db_schema.contacts as c " +
                        "inner join legacy_im_db_schema.users as own on " +
                            "c.id_owner=own.id " +
                        "inner join legacy_im_db_schema.roles as own_r on " +
                            "own.id_role=own_r.id " +
                        "inner join legacy_im_db_schema.users as per on " +
                            "c.id_person=per.id " +
                        "inner join legacy_im_db_schema.roles as per_r on " +
                            "per.id_role=per_r.id " +
                    "where c.id=? ";

    private static final String FIND_ALL_CONTACTS_FOR_USER_SQL_STATEMENT =
            "select " +
                    "   c.id as contact_id, " +
                    "   c.id_owner as id_owner, " +
                    "   c.id_person as id_person, " +
                    "   c.confirmed as confirmed, " +
                    "   own.id_role as owner_id_role, " +
                    "   per.id_role as pwerson_id_role, " +
                    "   own.enabled as owner_enabled, " +
                    "   per.enabled as person_enabled, " +
                    "   own.email as owner_email, " +
                    "   per.email as person_email, " +
                    "   own.firstname as owner_firstname, " +
                    "   per.firstname as person_firstname, " +
                    "   own.lastname as owner_lastname, " +
                    "   per.lastname as person_lastname, " +
                    "   own.nickname as owner_nickname, " +
                    "   per.nickname as person_nickname, " +
                    "   own.passwordhash as owner_pwd_hash, " +
                    "   per.passwordhash as person_pwd_hash, " +
                    "   own.salt as owner_salt, " +
                    "   per.salt as person_salt " +
                    "from legacy_im_db_schema.contacts as c " +
                    "   inner join legacy_im_db_schema.users as own on " +
                    "       c.id_owner=own.id " +
                    "   inner join legacy_im_db_schema.users as per on " +
                    "       c.id_person=per.id " +
                    "where id_owner = ? ";

    @Override
    public List<Contact> getContactsForUser(User user) throws DaoException{
        final Connection connection = getConnectionPool().takeConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<Contact> contacts = new LinkedList<>();

        try {
            ps = connection.prepareStatement(FIND_ALL_CONTACTS_FOR_USER_SQL_STATEMENT);
            ps.setLong(1, user.getId());
            resultSet = ps.executeQuery();
            while(resultSet.next()){
                final User owner = new User.UserBuilder()
                        .id(resultSet.getLong("id_owner"))
                        .firstName(resultSet.getString("owner_firstname"))
                        .lastName(resultSet.getString("owner_lastname"))
                        .nickName(resultSet.getString("owner_nickname"))
                        .email(resultSet.getString("owner_email"))
                        .enabled(resultSet.getBoolean("owner_enabled"))
                        .passwordHash(resultSet.getString("owner_pwd_hash"))
                        .salt(resultSet.getBytes("owner_salt"))
                        .role(new Role.RoleBuilder().id(1).name("USER").build()) //fixme
                        .build();

                final User person = new User.UserBuilder()
                        .id(resultSet.getLong("id_person"))
                        .firstName(resultSet.getString("person_firstname"))
                        .lastName(resultSet.getString("person_lastname"))
                        .nickName(resultSet.getString("person_nickname"))
                        .email(resultSet.getString("person_email"))
                        .enabled(resultSet.getBoolean("person_enabled"))
                        .passwordHash(resultSet.getString("person_pwd_hash"))
                        .salt(resultSet.getBytes("person_salt"))
                        .role(new Role.RoleBuilder().id(1).name("USER").build()) //fixme
                        .build();

                final Contact contact = new Contact.ContactBuilder()
                        .id(resultSet.getLong("contact_id"))
                        .confirmed(resultSet.getBoolean("confirmed"))
                        .owner(owner)
                        .person(person)
                        .build();
                contacts.add(contact);
            }

        } catch (SQLException | InstantiationException e) {
            throw new DaoException(e);
        } finally {
            getConnectionPool()
                    .closeConnection(resultSet, ps, connection);
        }
        return contacts;
    }

    @Override
    public Optional<Contact> findById(Long aLong) throws DaoException {

        final Connection connection = getConnectionPool().takeConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Contact contact = null;

        try {
            ps = connection.prepareStatement(SELECT_CONTACT_BY_ID);
            ps.setLong(1, aLong);
            resultSet = ps.executeQuery();

            if(resultSet.next()){
                final User owner = new User.UserBuilder()
                        .id(resultSet.getLong("id_owner"))
                        .firstName(resultSet.getString("owner_firstname"))
                        .lastName(resultSet.getString("owner_lastname"))
                        .nickName(resultSet.getString("owner_nickname"))
                        .email(resultSet.getString("owner_email"))
                        .enabled(resultSet.getBoolean("owner_enabled"))
                        .passwordHash(resultSet.getString("owner_pwd_hash"))
                        .role(new Role.RoleBuilder().id(1).name("USER").build()) //fixme
                        .salt(resultSet.getBytes("owner_salt"))
                        .build();

                final User person = new User.UserBuilder()
                        .id(resultSet.getLong("id_person"))
                        .firstName(resultSet.getString("person_firstname"))
                        .lastName(resultSet.getString("person_lastname"))
                        .nickName(resultSet.getString("person_nickname"))
                        .email(resultSet.getString("person_email"))
                        .enabled(resultSet.getBoolean("person_enabled"))
                        .passwordHash(resultSet.getString("person_pwd_hash"))
                        .role(new Role.RoleBuilder().id(1).name("USER").build()) //fixme
                        .salt(resultSet.getBytes("person_salt"))
                        .build();

                contact = new Contact.ContactBuilder()
                        .id(resultSet.getLong("id_contact"))
                        .confirmed(resultSet.getBoolean("confirmed"))
                        .owner(owner)
                        .person(person)
                        .build();
            }
        } catch (SQLException | InstantiationException e) {
            throw new DaoException(e);
        } finally {
            getConnectionPool()
                    .closeConnection(resultSet, ps, connection);
        }
        return Optional.ofNullable(contact);
    }

    @Override
    public boolean save(Contact contact) throws DaoException {
        final Connection connection = getConnectionPool().takeConnection();
        PreparedStatement ps = null;
        ResultSet set = null;
        try {
            ps = connection.prepareStatement(SAVE_CONTACT,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setLong(1, contact.getOwner().getId());
            ps.setLong(2, contact.getPerson().getId());
            ps.setBoolean(3, contact.getIsConfirmed());

            ps.executeUpdate();
            connection.commit();

            set = ps.getGeneratedKeys();
            set.next();
            return set.getInt(1) > 0;

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
                    .closeConnection(set, ps, connection);
        }
    }

    @Override
    public boolean update(Contact contact) throws DaoException {

        final Connection connection = getConnectionPool().takeConnection();
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(UPDATE_CONTACT);

            ps.setLong(1, contact.getOwner().getId());
            ps.setLong(2, contact.getPerson().getId());
            ps.setBoolean(3, contact.getIsConfirmed());
            ps.setLong(4, contact.getId());

            ps.executeUpdate();
            connection.commit();
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
        return true;
    }

    @Override
    public boolean delete(Contact contact) throws DaoException {
        final Connection connection = getConnectionPool().takeConnection();
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(DELETE_CONTACT);
            ps.setLong(1, contact.getId());
            ps.executeUpdate();
            connection.commit();
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
        return true;
    }

    @Override
    public Optional<Contact> getContactsFoOwnerAndPerson(User own, User per) throws DaoException {

        final Connection connection = getConnectionPool().takeConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Contact contact = null;

        try {
            ps = connection.prepareStatement(SELECT_CONTACT_BY_OWNER_AND_PERSON);
            ps.setLong(1, own.getId());
            ps.setLong(2, per.getId());
            resultSet = ps.executeQuery();

            if(resultSet.next()){
                final User owner = new User.UserBuilder()
                        .id(resultSet.getLong("id_owner"))
                        .firstName(resultSet.getString("owner_firstname"))
                        .lastName(resultSet.getString("owner_lastname"))
                        .nickName(resultSet.getString("owner_nickname"))
                        .email(resultSet.getString("owner_email"))
                        .enabled(resultSet.getBoolean("owner_enabled"))
                        .passwordHash(resultSet.getString("owner_pwd_hash"))
                        .salt(resultSet.getBytes("owner_salt"))
                        .role(new Role.RoleBuilder().id(1).name("USER").build()) //fixme
                        .build();

                final User person = new User.UserBuilder()
                        .id(resultSet.getLong("id_person"))
                        .firstName(resultSet.getString("person_firstname"))
                        .lastName(resultSet.getString("person_lastname"))
                        .nickName(resultSet.getString("person_nickname"))
                        .email(resultSet.getString("person_email"))
                        .enabled(resultSet.getBoolean("person_enabled"))
                        .passwordHash(resultSet.getString("person_pwd_hash"))
                        .role(new Role.RoleBuilder().id(1).name("USER").build()) //fixme
                        .salt(resultSet.getBytes("person_salt"))
                        .build();

                contact = new Contact.ContactBuilder()
                        .id(resultSet.getLong("id_contact"))
                        .confirmed(resultSet.getBoolean("confirmed"))
                        .owner(owner)
                        .person(person)
                        .build();
            }
        } catch (SQLException | InstantiationException e) {
            throw new DaoException(e);
        } finally {
            getConnectionPool()
                    .closeConnection(resultSet, ps, connection);
        }
        return Optional.ofNullable(contact);
    }
}
