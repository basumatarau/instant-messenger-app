package by.vironit.training.basumatarau.messengerService.exception;

public class NoEntityFound extends RuntimeException {
    public NoEntityFound() {
    }

    public NoEntityFound(String message) {
        super(message);
    }

    public NoEntityFound(String message, Throwable cause) {
        super(message, cause);
    }

    public NoEntityFound(Throwable cause) {
        super(cause);
    }

    public NoEntityFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
