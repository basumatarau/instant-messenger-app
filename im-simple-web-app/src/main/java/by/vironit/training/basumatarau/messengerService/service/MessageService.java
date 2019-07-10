package by.vironit.training.basumatarau.messengerService.service;

import by.vironit.training.basumatarau.messengerService.dto.ContactVo;
import by.vironit.training.basumatarau.messengerService.dto.IncomingMessageDto;
import by.vironit.training.basumatarau.messengerService.dto.MessageDto;
import by.vironit.training.basumatarau.messengerService.exception.ServiceException;
import by.vironit.training.basumatarau.messengerService.model.Contact;

import java.util.List;

public interface MessageService {
    List<MessageDto> getMessagesForContact(ContactVo contactDto) throws ServiceException;
    MessageDto persistMessage(IncomingMessageDto incomingMessage, Contact contact) throws ServiceException;
}
