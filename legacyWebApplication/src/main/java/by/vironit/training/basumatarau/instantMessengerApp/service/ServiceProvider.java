package by.vironit.training.basumatarau.instantMessengerApp.service;

import by.vironit.training.basumatarau.instantMessengerApp.service.impl.ContactServiceImpl;
import by.vironit.training.basumatarau.instantMessengerApp.service.impl.UserServiceImpl;

public enum  ServiceProvider {
    SERV;

    public final UserService userService;
    public final ContactService contactService;

    ServiceProvider(){
        this.contactService = new ContactServiceImpl();
        this.userService = new UserServiceImpl();
    }
}
