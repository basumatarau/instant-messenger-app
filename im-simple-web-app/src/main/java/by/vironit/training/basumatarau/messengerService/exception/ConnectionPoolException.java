package by.vironit.training.basumatarau.messengerService.exception;

public class ConnectionPoolException extends Exception {
    public ConnectionPoolException(){
        super();
    }
    public ConnectionPoolException(Exception e){
        super(e);
    }
    public ConnectionPoolException(String msg, Exception e){
        super(msg, e);
    }
}
