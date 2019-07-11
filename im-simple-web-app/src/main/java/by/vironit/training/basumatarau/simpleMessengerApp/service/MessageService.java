package by.vironit.training.basumatarau.simpleMessengerApp.service;

import by.vironit.training.basumatarau.simpleMessengerApp.dto.ContactVo;
import by.vironit.training.basumatarau.simpleMessengerApp.dto.IncomingMessageDto;
import by.vironit.training.basumatarau.simpleMessengerApp.dto.MessageDto;
import by.vironit.training.basumatarau.simpleMessengerApp.exception.ServiceException;
import by.vironit.training.basumatarau.simpleMessengerApp.model.Contact;

import java.util.List;

public interface MessageService {
    List<MessageDto> getMessagesForContact(ContactVo contactDto) throws ServiceException;
    MessageDto persistMessage(IncomingMessageDto incomingMessage, Contact contact) throws ServiceException;
}
