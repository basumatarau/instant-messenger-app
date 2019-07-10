package by.vironit.training.basumatarau.messengerService.service;

import by.vironit.training.basumatarau.messengerService.service.impl.ContactServiceImpl;
import by.vironit.training.basumatarau.messengerService.service.impl.MessageServiceImpl;
import by.vironit.training.basumatarau.messengerService.service.impl.UserServiceImpl;

public enum  ServiceProvider {
    SERV;

    public final UserService userService;
    public final ContactService contactService;
    public final MessageService messageService;

    ServiceProvider(){
        this.contactService = new ContactServiceImpl();
        this.userService = new UserServiceImpl();
        this.messageService = new MessageServiceImpl();
    }
}
