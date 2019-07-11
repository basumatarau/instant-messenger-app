package by.vironit.training.basumatarau.simpleMessengerApp.controller;

import by.vironit.training.basumatarau.simpleMessengerApp.command.*;

//command singletons

public enum Action {
    LOGINATION(new LoginationCommand()),
    SIGNUP(new SignUpCommand()),
    CONTACTLIST(new ContactListCommand()),
    CHAT(new ChatCommand()),
    ERROR(new ErrorCommand()),
    USERPROFILE(new UserProfileCommand()),
    BADREQUEST(new BadRequestCommand());

    private Command command;

    Action(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
