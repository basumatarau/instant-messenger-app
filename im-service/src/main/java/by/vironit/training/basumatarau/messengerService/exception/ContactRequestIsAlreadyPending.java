package by.vironit.training.basumatarau.messengerService.exception;

public class ContactRequestIsAlreadyPending extends RuntimeException {
    public ContactRequestIsAlreadyPending() {
    }

    public ContactRequestIsAlreadyPending(String message) {
        super(message);
    }

    public ContactRequestIsAlreadyPending(String message, Throwable cause) {
        super(message, cause);
    }

    public ContactRequestIsAlreadyPending(Throwable cause) {
        super(cause);
    }

    public ContactRequestIsAlreadyPending(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
