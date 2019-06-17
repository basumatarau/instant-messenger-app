package by.vironit.training.basumatarau.instantMessengerApp.dao.impl;

import by.vironit.training.basumatarau.instantMessengerApp.dao.RoleDao;
import by.vironit.training.basumatarau.instantMessengerApp.exception.DaoException;
import by.vironit.training.basumatarau.instantMessengerApp.model.Role;

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

    private static final String GET_ROLE_DAO_BY_ID =
            "select " +
                    "r.id as id, " +
                    "r.\"name\" as \"name\" " +
                    "from legacy_im_db_schema.roles as r " +
                    "where r.id = ? ";

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
            ps = connection.prepareStatement(GET_ROLE_DAO_BY_ID);
            ps.setInt(1, integer);
            resultSet = ps.executeQuery();

            if(resultSet.next()){
                role = new Role.RoleBuilder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build();
            }
        } catch (SQLException | InstantiationException e) {
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
                    return Optional.of(cashedRole.get());
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
