package by.vironit.training.basumatarau.instantMessengerApp.service;

import by.vironit.training.basumatarau.instantMessengerApp.dto.ContactVo;
import by.vironit.training.basumatarau.instantMessengerApp.dto.IncomingMessageDto;
import by.vironit.training.basumatarau.instantMessengerApp.dto.MessageDto;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ServiceException;
import by.vironit.training.basumatarau.instantMessengerApp.model.Contact;

import java.util.List;

public interface MessageService {
    List<MessageDto> getMessagesForContact(ContactVo contactDto) throws ServiceException;
    MessageDto persistMessage(IncomingMessageDto incomingMessage, Contact contact) throws ServiceException;
}
