package by.vironit.training.basumatarau.instantMessengerApp.service;

import by.vironit.training.basumatarau.instantMessengerApp.service.impl.UserServiceImpl;

public enum  ServiceProvider {
    SERV;

    public final UserService userService;

    ServiceProvider(){
        this.userService = new UserServiceImpl();
    }
}
