package by.vironit.training.basumatarau.instantMessengerApp.service;

import by.vironit.training.basumatarau.instantMessengerApp.dto.ContactDto;
import by.vironit.training.basumatarau.instantMessengerApp.dto.MessageDto;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ServiceException;

import java.util.List;

public interface MessageService {
    List<MessageDto> getMessagesForContact(ContactDto contactDto) throws ServiceException;
}
