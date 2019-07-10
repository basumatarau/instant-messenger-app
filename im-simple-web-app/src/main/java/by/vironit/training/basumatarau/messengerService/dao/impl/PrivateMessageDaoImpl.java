package by.vironit.training.basumatarau.messengerService.dao.impl;

import by.vironit.training.basumatarau.messengerService.dao.MessageDao;
import by.vironit.training.basumatarau.messengerService.exception.DaoException;
import by.vironit.training.basumatarau.messengerService.model.Contact;
import by.vironit.training.basumatarau.messengerService.model.Message;
import by.vironit.training.basumatarau.messengerService.model.PrivateMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class PrivateMessageDaoImpl extends BaseDao implements MessageDao {

    private static final Logger logger = LoggerFactory.getLogger(PrivateMessageDaoImpl.class);

    private static final String SAVE_MESSAGE =
            "INSERT INTO legacy_im_db_schema.messages " +
                    "(messagetype, body, timesent, id_contact, id_author, id_messageresource, id_chatroom) " +
                    "VALUES(?, ?, ?, ?, ?, null, null);";

    private static final String SELECT_MESSAGES_FOR_CONTACT =
            "select " +
                        "m.id as message_id, " +
                        "m.body as message_body, " +
                        "m.timesent as message_timesent " +
                    "from legacy_im_db_schema.messages as m " +
                        "where m.id_contact=? ";

    @Override
    public List<PrivateMessage> getMessagesForContact(Contact contact) throws DaoException{
        final Connection connection = getConnectionPool().takeConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        final List<PrivateMessage> messages = new LinkedList<>();

        try {
            ps = connection.prepareStatement(SELECT_MESSAGES_FOR_CONTACT);
            ps.setLong(1, contact.getId());
            resultSet = ps.executeQuery();
            while(resultSet.next()){
                final PrivateMessage message = new PrivateMessage
                        .PrivateMessageBuilder()
                        .contact(contact)
                        .author(contact.getOwner())
                        .id(resultSet.getLong("message_id"))
                        .body(resultSet.getString("message_body"))
                        .timeSent(new Date(resultSet.getLong("message_timesent")))
                        .build();
                messages.add(message);
            }
        } catch (SQLException | InstantiationException e) {
            logger.error("failed to fetch messages for contact:" + contact);
            throw new DaoException(e);
        } finally {
            getConnectionPool()
                    .closeConnection(resultSet, ps, connection);
        }
        return messages;
    }

    @Override
    public Optional<PrivateMessage> findById(Long aLong) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean save(PrivateMessage message) throws DaoException {
        final Connection connection = getConnectionPool().takeConnection();
        PreparedStatement ps = null;
        ResultSet set = null;
        try {
            ps = connection.prepareStatement(SAVE_MESSAGE,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, Message.PRIVATE_MESSAGE_TYPE);
            ps.setString(2, message.getBody());
            ps.setLong(3, message.getTimeSent().getTime());
            ps.setLong(4, message.getContact().getId());
            ps.setLong(5, message.getAuthor().getId());

            ps.executeUpdate();
            connection.commit();

            set = ps.getGeneratedKeys();
            set.next();
            return set.getInt(1) > 0;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("failed to persist message:" + message);
                throw new RuntimeException("failed to rollback transaction", ex);
            }
            throw new DaoException(e);
        } finally {
            getConnectionPool()
                    .closeConnection(set, ps, connection);
        }
    }

    @Override
    public boolean update(PrivateMessage bean) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(PrivateMessage bean) throws DaoException {
        return false;
    }
}
