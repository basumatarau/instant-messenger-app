package by.vironit.training.basumatarau.messengerService.exception;

public class UserAccountOccupied extends RuntimeException {
    public UserAccountOccupied() {
    }

    public UserAccountOccupied(String message) {
        super(message);
    }

    public UserAccountOccupied(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAccountOccupied(Throwable cause) {
        super(cause);
    }

    public UserAccountOccupied(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
