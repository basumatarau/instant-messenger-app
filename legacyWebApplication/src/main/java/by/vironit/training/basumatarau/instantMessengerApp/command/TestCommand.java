package by.vironit.training.basumatarau.instantMessengerApp.command;

import by.vironit.training.basumatarau.instantMessengerApp.controller.Model;
import by.vironit.training.basumatarau.instantMessengerApp.dao.CrudDao;
import by.vironit.training.basumatarau.instantMessengerApp.dao.DaoProvider;
import by.vironit.training.basumatarau.instantMessengerApp.exception.DaoException;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static by.vironit.training.basumatarau.instantMessengerApp.controller.Model.MODEL;
import static by.vironit.training.basumatarau.instantMessengerApp.controller.Model.USERLIST;

public class TestCommand implements Command {
    final CrudDao<User, Long> userDao = DaoProvider.DAO.userDao;

    @Override
    public Command process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, DaoException {

        final Optional<User> userById = userDao.findById(1L);

        ((List<User>) ((Model) req.getAttribute(MODEL)).get(USERLIST))
                .add(userById.orElse(null));
        return null;
    }

}
