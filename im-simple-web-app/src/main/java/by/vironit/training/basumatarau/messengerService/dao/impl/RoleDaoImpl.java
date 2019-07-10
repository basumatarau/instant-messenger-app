package by.vironit.training.basumatarau.messengerService.dao.impl;

import by.vironit.training.basumatarau.messengerService.dao.RoleDao;
import by.vironit.training.basumatarau.messengerService.exception.DaoException;
import by.vironit.training.basumatarau.messengerService.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class RoleDaoImpl extends BaseDao implements RoleDao {

    private static final Logger logger = LoggerFactory.getLogger(RoleDaoImpl.class);

    private static final String GET_ROLE_BY_ID_STATEMENT =
            "select " +
                    "r.id as id, " +
                    "r.\"name\" as \"name\" " +
                    "from legacy_im_db_schema.roles as r " +
                    "where r.id = ? ";

    private static final String GET_ROLE_BY_NAME_STATEMENT =
            "select " +
                    "r.id as id, " +
                    "r.\"name\" as \"name\" " +
                    "from legacy_im_db_schema.roles as r " +
                    "where r.\"name\" = ? ";

    private static Set<Role> CASH = new HashSet<>();
    private RoleDaoImpl() {}

    public static RoleDao getCashedProxy(){
        return ((RoleDao) Proxy.newProxyInstance(
                RoleDao.class.getClassLoader(),
                new Class[]{RoleDao.class},
                new RoleDaoInvocationHandler<>(CASH, new RoleDaoImpl())
        ));
    }

    @Override
    public Optional<Role> findById(Integer integer) throws DaoException {
        final Connection connection = getConnectionPool().takeConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Role role = null;

        try {
            ps = connection.prepareStatement(GET_ROLE_BY_ID_STATEMENT);
            ps.setInt(1, integer);
            resultSet = ps.executeQuery();

            if(resultSet.next()){
                role = new Role.RoleBuilder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build();
            }
        } catch (SQLException | InstantiationException e) {
            logger.error("failed to fetch role by id" + integer);
            throw new DaoException(e);
        } finally {
            getConnectionPool()
                    .closeConnection(resultSet, ps, connection);
        }
        return Optional.ofNullable(role);
    }

    @Override
    public boolean save(Role bean) throws DaoException {
        return false;
    }

    @Override
    public boolean update(Role bean) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Role bean) throws DaoException {
        return false;
    }

    @Override
    public Optional<Role> findByName(String name) throws DaoException {
        final Connection connection = getConnectionPool().takeConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Role role = null;

        try {
            ps = connection.prepareStatement(GET_ROLE_BY_NAME_STATEMENT);
            ps.setString(1, name);
            resultSet = ps.executeQuery();

            if(resultSet.next()){
                role = new Role.RoleBuilder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build();
            }
        } catch (SQLException | InstantiationException e) {
            logger.error("failed to fetch role by name" + name);
            throw new DaoException(e);
        } finally {
            getConnectionPool()
                    .closeConnection(resultSet, ps, connection);
        }
        return Optional.ofNullable(role);
    }

    private static class RoleDaoInvocationHandler<T extends Role>
            implements InvocationHandler{
        private final Set<T> cash;
        private final RoleDao target;

        public RoleDaoInvocationHandler(final Set<T> cash, RoleDao target) {
            this.cash = cash;
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            if(method.getName().equals("findById") && args.length == 1){
                final Optional<T> cashedRole  =
                                    cash
                                    .stream()
                                    .filter(r -> r.getId().equals(args[0]))
                                    .findAny();
                if(cashedRole.isPresent()){
                    return cashedRole;
                }else{
                    @SuppressWarnings("unchecked")
                    // findById method erasure has been strictly defined
                    // (arg.len == 1 + method name)
                    final Optional<T> optRole = ((Optional<T>) method.invoke(target, args));
                    optRole.ifPresent(cash::add);
                    return optRole;
                }
            }else if(method.getName().equals("findByName") && args.length == 1){
                final Optional<T> cashedRole  =
                        cash
                                .stream()
                                .filter(r -> r.getName().equals(args[0]))
                                .findAny();
                if(cashedRole.isPresent()){
                    return cashedRole;
                }else{
                    @SuppressWarnings("unchecked")
                    // findById method erasure has been strictly defined
                    // (arg.len == 1 + method name)
                    final Optional<T> optRole = ((Optional<T>) method.invoke(target, args));
                    optRole.ifPresent(cash::add);
                    return optRole;
                }
            }else{
                return method.invoke(target, args);
            }
        }
    }
}
