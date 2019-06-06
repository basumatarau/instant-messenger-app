package by.vironit.training.basumatarau.instantMessengerApp.exception;

public class UserNotFound extends ControllerException {
    public UserNotFound() {
    }

    public UserNotFound(Exception e) {
        super(e);
    }

    public UserNotFound(String message, Exception e) {
        super(message, e);
    }

    public UserNotFound(String message) {
        super(message);
    }
}
