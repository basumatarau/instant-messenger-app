package by.vironit.training.basumatarau.instantMessengerApp.controller;

import by.vironit.training.basumatarau.instantMessengerApp.command.*;

//command singletons

public enum Action {
    LOGINATION(new LoginationCommand()),
    SIGNUP(new SignUpCommand()),
    CONTACTLIST(new ContactListCommand()),
    CHAT(new ChatCommand()),
    ERROR(new ErrorCommand()),
    USERPROFILE(new UserProfileCommand()),
    TEST(new TestCommand()),
    BADREQUEST(new BadRequestCommand());

    private Command command;

    Action(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
