package by.vironit.training.basumatarau.messengerService.exception;

public class UserCredentialsOccupied extends ControllerException {
    public UserCredentialsOccupied() {
    }

    public UserCredentialsOccupied(Exception e) {
        super(e);
    }

    public UserCredentialsOccupied(String message, Exception e) {
        super(message, e);
    }

    public UserCredentialsOccupied(String message) {
        super(message);
    }
}
