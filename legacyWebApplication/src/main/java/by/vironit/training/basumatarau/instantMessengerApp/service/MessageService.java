package by.vironit.training.basumatarau.instantMessengerApp.service;

import by.vironit.training.basumatarau.instantMessengerApp.dto.ContactVo;
import by.vironit.training.basumatarau.instantMessengerApp.dto.MessageDto;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ServiceException;

import java.util.List;

public interface MessageService {
    List<MessageDto> getMessagesForContact(ContactVo contactDto) throws ServiceException;
}
