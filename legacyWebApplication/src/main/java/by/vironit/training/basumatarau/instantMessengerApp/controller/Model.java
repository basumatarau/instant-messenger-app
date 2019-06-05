package by.vironit.training.basumatarau.instantMessengerApp.controller;

import by.vironit.training.basumatarau.instantMessengerApp.model.User;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Model extends LinkedHashMap<String, Object>{
    public static final String MODEL = "model";
    public static final String ERRORS = "errors";
    public static final String USERLIST = "userList";


    public Model(){
        put(ERRORS, new LinkedList<Throwable>());
        put(USERLIST, new LinkedList<User>());
    }
}
